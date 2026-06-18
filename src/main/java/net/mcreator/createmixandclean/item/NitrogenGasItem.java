package net.mcreator.createmixandclean.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class NitrogenGasItem extends BucketItem {
	public NitrogenGasItem() {
		super(CreateMixAndCleanModFluids.NITROGEN_GAS.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)

		);
	}
}