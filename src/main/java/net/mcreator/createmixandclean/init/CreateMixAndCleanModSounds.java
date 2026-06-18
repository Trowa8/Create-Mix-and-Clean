/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, CreateMixAndCleanMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> RETCHING = REGISTRY.register("retching", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "retching")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MYCELIUM_MEN_MUSIC = REGISTRY.register("mycelium_men_music",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "mycelium_men_music")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ELECTROLYZER_PROCESSING = REGISTRY.register("electrolyzer_processing",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "electrolyzer_processing")));
	public static final DeferredHolder<SoundEvent, SoundEvent> COUGHING = REGISTRY.register("coughing", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "coughing")));
}