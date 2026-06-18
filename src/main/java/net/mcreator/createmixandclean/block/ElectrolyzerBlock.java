package net.mcreator.createmixandclean.block;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.mcreator.createmixandclean.block.entity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ElectrolyzerBlock extends HorizontalKineticBlock implements IBE<ElectrolyzerBlockEntity> {

    public ElectrolyzerBlock() {
        this(BlockBehaviour.Properties.of()
                .strength(3.5F, 6F)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .sound(SoundType.METAL));
    }

    public ElectrolyzerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public Class<ElectrolyzerBlockEntity> getBlockEntityClass() {
        return ElectrolyzerBlockEntity.class;
    }

    public BlockEntityType<? extends ElectrolyzerBlockEntity> getBlockEntityType() {
        return CreateMixAndCleanModBlockEntities.ELECTROLYZER.value();
    }

    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    public boolean hasShaftTowards(LevelReader level, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == getRotationAxis(state);
    }

    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return getBlockEntityOptional(level, pos)
                .map(be -> (int) (be.getEnergyStored() / 10000F * 15F))
                .orElse(0);
    }

    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        return getBlockEntityOptional(level, pos)
                .map(be -> be.triggerEvent(eventId, eventParam))
                .orElse(false);
    }
}