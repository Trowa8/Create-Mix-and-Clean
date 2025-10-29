
package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class CombinedCopperItem extends Item {
	public CombinedCopperItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
