
package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class HydrochloricAcidItem extends BucketItem {
	public HydrochloricAcidItem() {
		super(CreateMixAndCleanModFluids.HYDROCHLORIC_ACID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
	}
}
