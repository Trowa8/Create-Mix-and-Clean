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

    public void invalidateAround(ServerLevel level, BlockPos changedPos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = changedPos.relative(dir);
            GasPocket pocket = pocketByCell.get(neighbor);
            if (pocket != null) {
                dissolvePocket(pocket);
                for (BlockPos cell : pocket.cells) {
                    GasTickScheduler.wake(level, cell);
                }
            }
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

    private GasPocket floodFill(ServerLevel level, BlockPos seed) {
        GasPocket pocket = new GasPocket();
        int cap = CreateMixAndCleanGasConfig.MAX_POCKET_CELLS.get();

        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        BlockPos seedImmutable = seed.immutable();
        queue.add(seedImmutable);
        visited.add(seedImmutable);

        while (!queue.isEmpty() && pocket.cellCount() < cap) {
            BlockPos current = queue.poll();
            BlockState state = level.getBlockState(current);
            boolean passable = state.isAir() || GasCellFactory.at(level, current) != null;
            if (!passable) continue;

            pocket.cells.add(current);
            if (level.canSeeSky(current)) pocket.skyExposed = true;

            for (Direction dir : Direction.values()) {
                BlockPos next = current.relative(dir).immutable();
                if (visited.add(next)) {
                    queue.add(next);
                }
            }
        }

        pocket.capped = pocket.cellCount() >= cap;
        for (BlockPos cell : pocket.cells) {
            pocketByCell.put(cell, pocket);
        }
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
            pocket.settled = true;
            for (BlockPos cell : pocket.cells) {
                GasTickScheduler.markSettled(level, cell);
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
        if (manager == null || manager.dirtyPockets.isEmpty()) return;

        long tick = serverLevel.getGameTime();
        for (GasPocket pocket : new HashSet<>(manager.dirtyPockets)) {
            manager.recompute(serverLevel, pocket, tick);
        }
    }
}
