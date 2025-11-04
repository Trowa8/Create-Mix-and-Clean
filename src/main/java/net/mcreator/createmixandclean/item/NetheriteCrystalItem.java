package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class NetheriteCrystalItem extends Item {
	public NetheriteCrystalItem() {
		super(new Item.Properties().fireResistant());
	}

	@Override
	public boolean isPiglinCurrency(ItemStack stack) {
		return true;
	}
}