package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class MusicDiscMyceliumMenItem extends Item {
	public MusicDiscMyceliumMenItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(CreateMixAndCleanMod.MODID, "music_disc_mycelium_men"))));
	}
}