
package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class CombinedZincItem extends Item {
	public CombinedZincItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
