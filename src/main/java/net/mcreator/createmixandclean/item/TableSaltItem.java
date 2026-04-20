package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class TableSaltItem extends Item {
	public TableSaltItem() {
		super(new Item.Properties().food((new FoodProperties.Builder()).nutrition(0).saturationMod(0.5f).alwaysEat().meat().build()));
	}
}