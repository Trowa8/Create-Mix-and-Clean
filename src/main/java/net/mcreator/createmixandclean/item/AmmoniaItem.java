package net.mcreator.createmixandclean.item;

import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

import javax.annotation.Nullable;

public class AmmoniaItem extends BucketItem {
	public AmmoniaItem() {
		super(CreateMixAndCleanModFluids.AMMONIA.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
		);
	}

	public FluidBucketWrapper initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new FluidBucketWrapper(stack);
	}
}