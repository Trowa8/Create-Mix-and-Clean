package net.mcreator.createmixandclean.gas.tick;

import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

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
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;

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

            if (!isSettled(serverLevel, pos) && ThreadLocalRandom.current().nextInt(1, 5) <= 2) {
                enqueue(serverLevel, pos);
            }
        }
    }
}
