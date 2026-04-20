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

public class OxygenGasBlock extends LiquidBlock {
	public OxygenGasBlock() {
		super(() -> CreateMixAndCleanModFluids.OXYGEN_GAS.get(),
				BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(99f).ignitedByLava().noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 20;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 10;
	}
}