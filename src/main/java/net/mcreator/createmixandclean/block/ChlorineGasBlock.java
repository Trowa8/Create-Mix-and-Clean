
package net.mcreator.createmixandclean.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class ChlorineGasBlock extends LiquidBlock {
	public ChlorineGasBlock() {
		super(() -> CreateMixAndCleanModFluids.CHLORINE_GAS.get(),
				BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}
}
