package net.mcreator.createmixandclean.gas.tick;

import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.mcreator.createmixandclean.gas.GasCellAccess;
import net.mcreator.createmixandclean.gas.GasCellFactory;
import net.mcreator.createmixandclean.gas.GasCellRegistry;
import net.mcreator.createmixandclean.gas.GasType;
import net.minecraft.core.particles.DustParticleOptions;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class GasTickScheduler {

    private static final Map<ServerLevel, ArrayDeque<BlockPos>> QUEUES = new HashMap<>();
    private static final Map<ServerLevel, Set<BlockPos>> QUEUED_SET = new HashMap<>();
    private static final Map<ServerLevel, Set<BlockPos>> SETTLED = new HashMap<>();

    public static void enqueue(ServerLevel level, BlockPos pos) {
        if (isSettled(level, pos)) return;
        BlockPos immutable = pos.immutable();
        Set<BlockPos> queuedSet = QUEUED_SET.computeIfAbsent(level, l -> new HashSet<>());
        if (!queuedSet.add(immutable)) return;
        QUEUES.computeIfAbsent(level, l -> new ArrayDeque<>()).addLast(immutable);
    }

    public static void markSettled(ServerLevel level, BlockPos pos) {
        SETTLED.computeIfAbsent(level, l -> new HashSet<>()).add(pos.immutable());
    }

    public static void wake(ServerLevel level, BlockPos pos) {
        Set<BlockPos> settled = SETTLED.get(level);
        if (settled != null) settled.remove(pos.immutable());
        enqueue(level, pos);
    }

    private static boolean isSettled(ServerLevel level, BlockPos pos) {
        Set<BlockPos> settled = SETTLED.get(level);
        return settled != null && settled.contains(pos);
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;
            for (BlockPos cellPos : GasCellRegistry.get(serverLevel)) {
                enqueue(serverLevel, cellPos);
            }
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            QUEUES.remove(serverLevel);
            QUEUED_SET.remove(serverLevel);
            SETTLED.remove(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;

        if (serverLevel.getGameTime() % 5 == 0) {
            Set<BlockPos> cellsToCheck = new HashSet<>(GasCellRegistry.get(serverLevel));
            Set<BlockPos> neighbors = new HashSet<>();
            for (BlockPos pos : cellsToCheck) {
                neighbors.add(pos.above());
                neighbors.add(pos.below());
                neighbors.add(pos.north());
                neighbors.add(pos.south());
                neighbors.add(pos.east());
                neighbors.add(pos.west());
            }
            cellsToCheck.addAll(neighbors);

            for (BlockPos cellPos : cellsToCheck) {
                if (ThreadLocalRandom.current().nextInt(10) != 0) continue;

                GasCellAccess access = GasCellFactory.at(serverLevel, cellPos);
                if (access == null || access.isEmpty()) continue;

                float totalConc = 0f;
                Map<GasType, Float> presentGases = new HashMap<>();

                for (GasType gas : access.getPresentGases()) {
                    float conc = access.getConcentration(gas);
                    if (conc > 0f) {
                        presentGases.put(gas, conc);
                        totalConc += conc;
                    }
                }

                if (totalConc <= 0f) continue;

                float roll = ThreadLocalRandom.current().nextFloat() * totalConc;
                float cumulative = 0f;

                for (Map.Entry<GasType, Float> entry : presentGases.entrySet()) {
                    cumulative += entry.getValue();
                    if (roll <= cumulative) {
                        GasType chosenGas = entry.getKey();
                        float[] c = chosenGas.getFallbackColor();

                        serverLevel.sendParticles(
                                new DustParticleOptions(new Vector3f(c[0], c[1], c[2]), 1.0f),
                                cellPos.getX() + 0.5, cellPos.getY() + 0.5, cellPos.getZ() + 0.5,
                                1,
                                0.25, 0.25, 0.25,
                                0.0
                        );
                        break;
                    }
                }
            }
        }

        ArrayDeque<BlockPos> queue = QUEUES.get(serverLevel);
        Set<BlockPos> queuedSet = QUEUED_SET.get(serverLevel);
        if (queue == null || queue.isEmpty()) return;

        int budget = CreateMixAndCleanGasConfig.MAX_CELL_UPDATES_PER_TICK.get();
        int processed = 0;

        while (processed < budget && !queue.isEmpty()) {
            BlockPos pos = queue.pollFirst();
            if (queuedSet != null) queuedSet.remove(pos);
            if (isSettled(serverLevel, pos)) continue;

            GasDiffuseStep.run(serverLevel, pos);
            processed++;
        }
    }
}
