/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<SoundEvent> RETCHING = REGISTRY.register("retching", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "retching")));
}