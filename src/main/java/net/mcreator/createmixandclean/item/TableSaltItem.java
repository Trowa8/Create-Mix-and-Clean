package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class TableSaltItem extends Item {
	public TableSaltItem() {
		super(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1f).alwaysEat().meat().build()));
	}
}