package net.mcreator.createmixandclean.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class MusicDiscMyceliumMenItem extends RecordItem {
	public MusicDiscMyceliumMenItem() {
		super(9, () -> ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.parse("create_mix_and_clean:mycelium_men_music")), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2300);
	}
}