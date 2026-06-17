package net.mcreator.createmixandclean.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class BauxiteBlock extends Block {
	public BauxiteBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(1f, 10f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}
}