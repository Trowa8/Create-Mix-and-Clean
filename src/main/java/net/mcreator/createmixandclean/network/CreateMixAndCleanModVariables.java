package net.mcreator.createmixandclean.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

@EventBusSubscriber
public class CreateMixAndCleanModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CreateMixAndCleanMod.MODID);
	public static boolean Mekanism_compat = false;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
	}
}