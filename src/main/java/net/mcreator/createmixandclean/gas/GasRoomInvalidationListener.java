package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.pocket.GasPocketManager;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class GasRoomInvalidationListener {

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            GasPocketManager.get(level).invalidateAround(level, event.getPos());
        }
    }
}
