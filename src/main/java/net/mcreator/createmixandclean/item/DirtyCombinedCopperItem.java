
package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class DirtyCombinedCopperItem extends Item {
	public DirtyCombinedCopperItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
