package net.mcreator.createmixandclean.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class BauxiteBlock extends Block {
	public BauxiteBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(1f, 10f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT));
	}
}