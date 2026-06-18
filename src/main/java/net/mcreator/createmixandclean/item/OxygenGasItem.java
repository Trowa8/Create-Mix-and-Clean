package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class OxygenGasItem extends BucketItem {
	public OxygenGasItem() {
		super(CreateMixAndCleanModFluids.OXYGEN_GAS.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)

		);
	}
}