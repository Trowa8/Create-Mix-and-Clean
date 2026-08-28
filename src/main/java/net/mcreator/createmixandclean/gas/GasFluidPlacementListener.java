package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
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
public class GasFluidPlacementListener {

    @SubscribeEvent
    public static void onFluidPlace(BlockEvent.FluidPlaceBlockEvent event) {
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;

        LevelAccessor level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return;

        Fluid fluid = event.getNewState().getFluidState().getType();
        GasType gasType = GasFluidLookup.get(fluid);
        if (gasType == null) return;

        BlockPos pos = event.getPos();
        event.setNewState(Blocks.AIR.defaultBlockState());

        CreateMixAndCleanMod.queueServerWork(1, () -> {
            GasCellAccess access = GasCellFactory.at(serverLevel, pos);
            if (access != null) {
                access.setConcentration(gasType, 15f);
                GasTickScheduler.wake(serverLevel, pos);
            }
        });
    }
}
