package net.mcreator.createmixandclean.network;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreateMixAndCleanModVariables {
	public static boolean Mekanism_compat = false;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
	}
}