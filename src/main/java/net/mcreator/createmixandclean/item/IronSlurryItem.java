package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class IronSlurryItem extends BucketItem {
	public IronSlurryItem() {
		super(CreateMixAndCleanModFluids.IRON_SLURRY.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)

		);
	}
}