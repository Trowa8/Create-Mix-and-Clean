package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.tick.GasTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = "create_mix_and_clean")
public final class GasConversionScheduler {

    private static final Map<ServerLevel, Set<BlockPos>> QUEUED = new HashMap<>();

    private GasConversionScheduler() {}

    public static void enqueue(ServerLevel level, BlockPos pos) {
        QUEUED.computeIfAbsent(level, l -> new HashSet<>()).add(pos.immutable());
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        Set<BlockPos> queue = QUEUED.get(level);
        if (queue == null || queue.isEmpty()) return;

        Set<BlockPos> current = queue;
        QUEUED.put(level, new HashSet<>());

        for (BlockPos pos : current) {
            Fluid fluid = level.getFluidState(pos).getType();
            GasType gasType = GasRegistry.getGasType(fluid);
            if (gasType == null) continue;

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);

            GasCellAccess access = GasCellFactory.at(level, pos);
            if (access != null) {
                access.setConcentration(gasType, 15f);
                GasTickScheduler.wake(level, pos);
            }
        }
    }
}