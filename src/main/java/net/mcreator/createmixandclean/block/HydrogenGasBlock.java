package net.mcreator.createmixandclean.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;

public class HydrogenGasBlock extends LiquidBlock {
	public HydrogenGasBlock() {
		super(() -> CreateMixAndCleanModFluids.HYDROGEN_GAS.get(), BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 10;
	}
}