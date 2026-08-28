package net.mcreator.createmixandclean.gas.block;

import net.mcreator.createmixandclean.gas.block.entity.GasCellBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class MixtureGasBlock extends Block implements EntityBlock {

    public MixtureGasBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .noCollission()
                .noLootTable()
                .replaceable()
                .sound(SoundType.EMPTY)
                .pushReaction(PushReaction.DESTROY)
                .strength(-1f, 3600000f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GasCellBlockEntity(pos, state);
    }
}
