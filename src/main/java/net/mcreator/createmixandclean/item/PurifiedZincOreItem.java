
package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class PurifiedZincOreItem extends Item {
	public PurifiedZincOreItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
