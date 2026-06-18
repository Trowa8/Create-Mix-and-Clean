package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class LiquidAirItem extends BucketItem {
	public LiquidAirItem() {
		super(CreateMixAndCleanModFluids.LIQUID_AIR.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)

		);
	}
}