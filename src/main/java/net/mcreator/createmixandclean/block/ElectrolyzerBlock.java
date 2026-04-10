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

public class ElectrolyzerBlock extends HorizontalKineticBlock
        implements IBE<ElectrolyzerBlockEntity> {

    // No-arg constructor so MCreator's ElectrolyzerBlock::new in
    // CreateMixAndCleanModBlocks.java still compiles after regeneration
    public ElectrolyzerBlock() {
        this(BlockBehaviour.Properties.of()
                .strength(3.5f, 6f)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .sound(SoundType.METAL));
    }

    public ElectrolyzerBlock(Properties properties) {
        super(properties);
    }

    // ── IBE ─────────────────────────────────────────────────────────────────
    // IBE implements EntityBlock for us — no need for newBlockEntity or
    // EntityBlock separately.

    @Override
    public Class<ElectrolyzerBlockEntity> getBlockEntityClass() {
        return ElectrolyzerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectrolyzerBlockEntity> getBlockEntityType() {
        return CreateMixAndCleanModBlockEntities.ELECTROLYZER.get();
    }

    // ── Kinetics ─────────────────────────────────────────────────────────────
    // Shaft runs along the axis of the horizontal facing direction,
    // connecting on both sides (N↔S or E↔W depending on placement).

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos,
                                    BlockState state, Direction face) {
        return face.getAxis() == getRotationAxis(state);
    }

    // ── From MCreator element config ─────────────────────────────────────────
    // lightOpacity: 12
    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 12;
    }

    // inventoryComparatorPower: true — output based on FE stored
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return getBlockEntityOptional(world, pos)
                .map(be -> (int) (((float) be.getEnergyStored() / 10000f) * 15))
                .orElse(0);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world,
                              BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        return getBlockEntityOptional(world, pos)
                .map(be -> be.triggerEvent(eventID, eventParam))
                .orElse(false);
    }
}