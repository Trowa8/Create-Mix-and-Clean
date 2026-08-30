package net.mcreator.createmixandclean.gas.pocket;

import net.mcreator.createmixandclean.gas.GasCellAccess;
import net.mcreator.createmixandclean.gas.GasCellFactory;
import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.mcreator.createmixandclean.gas.tick.GasTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class GasPocketManager {

    private static final Map<Level, GasPocketManager> INSTANCES = new HashMap<>();

    private final Map<BlockPos, GasPocket> pocketByCell = new HashMap<>();
    private final Set<GasPocket> dirtyPockets = new HashSet<>();

    public static GasPocketManager get(Level level) {
        return INSTANCES.computeIfAbsent(level, l -> new GasPocketManager());
    }

    public void markDirty(BlockPos pos) {
        GasPocket pocket = pocketByCell.get(pos.immutable());
        if (pocket != null) {
            dirtyPockets.add(pocket);
            pocket.settled = false;
            pocket.stableStreak = 0;
        }
    }

    private boolean isRebuilding = false;

    public void invalidateAround(ServerLevel level, BlockPos changedPos) {
        if (isRebuilding) return;
        isRebuilding = true;
    
        try {
            Set<GasPocket> affectedPockets = new HashSet<>();
            
            for (int dx = -2; dx <= 2; dx++) {
                for (int dy = -2; dy <= 2; dy++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        BlockPos checkPos = changedPos.offset(dx, dy, dz);
                        GasPocket pocket = pocketByCell.get(checkPos.immutable());
                        if (pocket != null) {
                            affectedPockets.add(pocket);
                        }
                    }
                }
            }
            
            if (!affectedPockets.isEmpty()) {
                net.mcreator.createmixandclean.CreateMixAndCleanMod.LOGGER.info(
                    "Invalidating {} gas pockets around block change at {}", 
                    affectedPockets.size(), changedPos
                );
            }
            
            for (GasPocket pocket : affectedPockets) {
                pocket.settled = false;
                pocket.stableStreak = 0;
                
                net.mcreator.createmixandclean.CreateMixAndCleanMod.LOGGER.info(
                    "Waking {} cells in pocket due to topology change",
                    pocket.cellCount()
                );
    
                java.util.List<BlockPos> boundaryFirst = new java.util.ArrayList<>();
                java.util.List<BlockPos> interior = new java.util.ArrayList<>();
                
                for (BlockPos cell : pocket.cells) {
                    double distSq = changedPos.distSqr(cell);
                    if (distSq <= 3) {
                        boundaryFirst.add(cell);
                    } else {
                        interior.add(cell);
                    }
                }
                
                dissolvePocket(pocket);
    
                for (BlockPos cell : boundaryFirst) {
                    GasTickScheduler.wake(level, cell);
                }
                
                for (BlockPos cell : interior) {
                    GasTickScheduler.enqueue(level, cell);
                }
            }
            
            GasPocket rebuiltPocket = getOrBuildPocket(level, changedPos);
            markDirty(changedPos);
            net.mcreator.createmixandclean.CreateMixAndCleanMod.LOGGER.info(
                "Rebuilt pocket at {} with {} cells after topology change",
                changedPos, rebuiltPocket.cellCount()
            );
            
        } finally {
            isRebuilding = false;
        }
    }

    private void dissolvePocket(GasPocket pocket) {
        for (BlockPos cell : pocket.cells) {
            pocketByCell.remove(cell);
        }
        dirtyPockets.remove(pocket);
    }

    public GasPocket getOrBuildPocket(ServerLevel level, BlockPos seed) {
        GasPocket existing = pocketByCell.get(seed.immutable());
        if (existing != null) return existing;
        return floodFill(level, seed);
    }

    private GasType getPredominantGas(ServerLevel level, BlockPos pos) {
        GasCellAccess access = GasCellFactory.at(level, pos);
        if (access == null) return null;
        GasType dominant = null;
        float max = -1f;
        for (GasType type : access.getPresentGases()) {
            float conc = access.getConcentration(type);
            if (conc > max) {
                max = conc;
                dominant = type;
            }
        }
        return dominant;
    }

    private Direction[] getSortedDirections(GasType type) {
        Direction[] dirs = Direction.values().clone();
        if (type == null) return dirs;

        if (type.getMolarMass() < 25f) {
            return new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN};
        } 
        else if (type.getMolarMass() > 35f) {
            return new Direction[]{Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP};
        }
        return dirs;
    }

    private void equalizePocket(ServerLevel level, GasPocket pocket) {
        Map<GasType, Float> totals = new HashMap<>();
        for (BlockPos cell : pocket.cells) {
            GasCellAccess access = GasCellFactory.at(level, cell);
            if (access == null) continue;
            for (GasType gas : access.getPresentGases()) {
                totals.merge(gas, access.getConcentration(gas), Float::sum);
            }
        }
        if (totals.isEmpty()) return;
    
        for (BlockPos cell : pocket.cells) {
            GasCellAccess access = GasCellFactory.at(level, cell);
            if (access == null) continue;
            for (Map.Entry<GasType, Float> entry : totals.entrySet()) {
                float avg = entry.getValue() / pocket.cellCount();
                access.setConcentration(entry.getKey(), avg);
            }
            GasTickScheduler.wake(level, cell);
        }
    }

    private GasPocket floodFill(ServerLevel level, BlockPos seed) {
        GasPocket pocket = new GasPocket();
        int cap = CreateMixAndCleanGasConfig.MAX_POCKET_CELLS.get();
        int maxAirPerTick = 4; 
        int airClaimed = 0;

        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        BlockPos seedImmutable = seed.immutable();
        queue.add(seedImmutable);
        visited.add(seedImmutable);

        GasType predominant = getPredominantGas(level, seedImmutable);
        Direction[] searchDirs = getSortedDirections(predominant);

        while (!queue.isEmpty() && pocket.cellCount() < cap) {
            BlockPos current = queue.poll();
            BlockState state = level.getBlockState(current);
            GasCellAccess access = GasCellFactory.at(level, current);
            
            boolean isExistingGas = access != null && !access.isEmpty();
            boolean passable = state.isAir() || access != null;
            
            if (!passable) continue;

            if (!isExistingGas) {
                if (airClaimed >= maxAirPerTick) {
                    pocket.wantsToExpand = true;
                    continue;
                }
                airClaimed++;
            }

            pocket.cells.add(current);
            if (level.canSeeSky(current)) pocket.skyExposed = true;

            for (Direction dir : searchDirs) {
                BlockPos next = current.relative(dir).immutable();
                // Prevent freezing at chunk borders
                if (!level.isLoaded(next)) continue; 
                if (visited.add(next)) {
                    queue.add(next);
                }
            }
        }

        pocket.capped = pocket.cellCount() >= cap;
        for (BlockPos cell : pocket.cells) {
            pocketByCell.put(cell, pocket);
        }
        equalizePocket(level, pocket);
        return pocket;
    }

    public void recompute(ServerLevel level, GasPocket pocket, long currentTick) {
        int interval = CreateMixAndCleanGasConfig.POCKET_RECOMPUTE_INTERVAL.get();
        if (currentTick - pocket.lastRecomputeTick < interval) return;
        pocket.lastRecomputeTick = currentTick;

        Map<GasType, Float> totals = new HashMap<>();
        for (BlockPos cell : pocket.cells) {
            GasCellAccess access = GasCellFactory.at(level, cell);
            if (access == null) continue;
            for (GasType gas : access.getPresentGases()) {
                totals.merge(gas, access.getConcentration(gas), Float::sum);
            }
        }

        float capacity = pocket.cellCount() * 15f;
        Map<GasType, Float> newComposition = new HashMap<>();
        totals.forEach((gas, sum) -> newComposition.put(gas, capacity <= 0 ? 0f : sum / capacity));

        boolean stable = isStable(pocket.composition, newComposition);
        pocket.composition.clear();
        newComposition.forEach(pocket.composition::put);

        pocket.stableStreak = stable ? pocket.stableStreak + 1 : 0;

        if (CreateMixAndCleanGasConfig.SETTLING_ENABLED.get()
                && pocket.stableStreak >= CreateMixAndCleanGasConfig.SETTLE_STABLE_CYCLES.get()
                && !pocket.settled) {
            boolean allCellsCaughtUp = pocket.cells.stream().allMatch(cell -> {
                GasCellAccess access = GasCellFactory.at(level, cell);
                if (access == null) return true;
                float total = access.getTotalLevel();
                float expectedShare = totals.values().stream().reduce(0f, Float::sum) / pocket.cellCount();
                return Math.abs(total - expectedShare) <= CreateMixAndCleanGasConfig.SETTLE_EPSILON.get().floatValue() * 15f;
            });
        
            if (allCellsCaughtUp) {
                pocket.settled = true;
                for (BlockPos cell : pocket.cells){
                    GasTickScheduler.markSettled(level, cell);
                }
            } else {
                pocket.stableStreak = 0;
            }
        }
    }

    private boolean isStable(Map<GasType, Float> previous, Map<GasType, Float> current) {
        float epsilon = CreateMixAndCleanGasConfig.SETTLE_EPSILON.get().floatValue();
        for (GasType gas : GasType.values()) {
            float prev = previous.getOrDefault(gas, 0f);
            float now = current.getOrDefault(gas, 0f);
            if (Math.abs(prev - now) > epsilon) return false;
        }
        return true;
    }

    public Map<GasType, Float> getComposition(ServerLevel level, BlockPos pos) {
        GasPocket pocket = pocketByCell.get(pos.immutable());
        if (pocket == null) pocket = getOrBuildPocket(level, pos);
        recompute(level, pocket, level.getGameTime());
        return pocket.composition;
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;

        GasPocketManager manager = INSTANCES.get(serverLevel);
        if (manager == null) return;
        
        long tick = serverLevel.getGameTime();

        for (GasPocket pocket : manager.getPockets()) {
            if (pocket.wantsToExpand && tick >= pocket.nextExpansionTick) {
                pocket.nextExpansionTick = tick + 20;
                manager.expandPocket(serverLevel, pocket, 4);
            }
        }

        if (manager.dirtyPockets.isEmpty()) return;
        for (GasPocket pocket : new java.util.HashSet<>(manager.dirtyPockets)) {
            manager.recompute(serverLevel, pocket, tick);
        }
    }

    public Set<GasPocket> getPockets() {
        return new HashSet<>(pocketByCell.values());
    }

    @SubscribeEvent
    public static void onLevelUnload(net.neoforged.neoforge.event.level.LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            INSTANCES.remove(serverLevel);
        }
    }

    public void expandPocket(ServerLevel level, GasPocket pocket, int amountToAdd) {
    int cap = CreateMixAndCleanGasConfig.MAX_POCKET_CELLS.get();
    if (pocket.cells.size() >= cap) {
        pocket.wantsToExpand = false;
        pocket.capped = true;
        return;
    }

    float totalGas = 0f;
    for (BlockPos cell : pocket.cells) {
        GasCellAccess access = GasCellFactory.at(level, cell);
        if (access != null) totalGas += access.getTotalLevel();
    }
    
    if (totalGas / (pocket.cells.size() + 1) < 1.0f) {
        pocket.wantsToExpand = false;
        return;
    }

    GasType predominant = null;
    for (BlockPos p : pocket.cells) {
        predominant = getPredominantGas(level, p);
        if (predominant != null) break;
    }
    
    Direction[] searchDirs = getSortedDirections(predominant);
    
    java.util.List<BlockPos> sortedCells = new java.util.ArrayList<>(pocket.cells);
    if (predominant != null) {
        if (predominant.getMolarMass() < 25f) {
            sortedCells.sort((p1, p2) -> Integer.compare(p2.getY(), p1.getY()));
        } else if (predominant.getMolarMass() > 35f) {
            sortedCells.sort((p1, p2) -> Integer.compare(p1.getY(), p2.getY()));
        }
    }

    boolean foundSpace = false;
    java.util.List<BlockPos> newCells = new java.util.ArrayList<>();
    for (BlockPos cell : sortedCells) {
        for (Direction dir : searchDirs) {
                BlockPos next = cell.relative(dir).immutable();
                if (!level.isLoaded(next) || pocket.cells.contains(next) || newCells.contains(next)) continue;

                BlockState state = level.getBlockState(next);
                GasCellAccess access = GasCellFactory.at(level, next);
                
                if (state.isAir() || access != null) {
                    newCells.add(next);
                    foundSpace = true;
                    if (newCells.size() >= amountToAdd) break;
                }
            }
            if (newCells.size() >= amountToAdd) break;
        }

        if (!foundSpace) {
            pocket.wantsToExpand = false;
            return;
        }

        for (BlockPos next : newCells) {
            pocket.cells.add(next);
            pocketByCell.put(next, pocket);
            if (level.canSeeSky(next)) pocket.skyExposed = true;
        }

        pocket.wantsToExpand = true; 
        equalizePocket(level, pocket);
    }
}
