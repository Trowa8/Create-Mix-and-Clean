package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.mcreator.createmixandclean.gas.pocket.GasPocketManager;
import net.mcreator.createmixandclean.gas.tick.GasTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class GasEventListeners {

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            Fluid fluid = level.getFluidState(event.getPos()).getType();
            GasType gasType = GasRegistry.getGasType(fluid);
            if (gasType != null) {
                GasConversionScheduler.enqueue(level, event.getPos());
                CreateMixAndCleanMod.LOGGER.info("Queued gas conversion at {}", event.getPos());
            }
            
            GasPocketManager.get(level).invalidateAround(level, event.getPos());
            CreateMixAndCleanMod.LOGGER.info("NeighborNotifyEvent fired at {}", event.getPos());
        }
    }

    @SubscribeEvent
    public static void onFluidPlace(BlockEvent.FluidPlaceBlockEvent event) {
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;

        LevelAccessor level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return;

        Fluid fluid = event.getNewState().getFluidState().getType();
        GasType gasType = GasRegistry.getGasType(fluid);
        if (gasType == null) return;

        BlockPos pos = event.getPos();
        event.setNewState(Blocks.AIR.defaultBlockState());

        CreateMixAndCleanMod.LOGGER.info("FluidPlaceBlockEvent fired at {}", event.getPos());

        CreateMixAndCleanMod.queueServerWork(1, () -> {
            GasCellAccess access = GasCellFactory.at(serverLevel, pos);
            if (access != null) {
                access.setConcentration(gasType, 15f);
                GasTickScheduler.wake(serverLevel, pos);
            }
        });
    }
}