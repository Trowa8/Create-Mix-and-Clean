package net.mcreator.createmixandclean.block;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.mcreator.createmixandclean.blockentity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.PickaxeItem;

public class ElectrolyzerBlock extends HorizontalKineticBlock
        implements IBE<ElectrolyzerBlockEntity> {

    public ElectrolyzerBlock() {
        this(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of()
                .strength(3.5f, 6f)
                .requiresCorrectToolForDrops(PickaxeItem)
                .sound(net.minecraft.world.level.block.SoundType.METAL));
    }

    public ElectrolyzerBlock(Properties properties) {
        super(properties);
    }

    // ── IBE wiring ──────────────────────────────────────────────
    @Override
    public Class<ElectrolyzerBlockEntity> getBlockEntityClass() {
        return ElectrolyzerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectrolyzerBlockEntity> getBlockEntityType() {
        return CreateMixAndCleanModBlockEntities.ELECTROLYZER.get();
    }

    // ── Kinetics ─────────────────────────────────────────────────
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        // Shaft runs along the horizontal facing axis (N↔S or E↔W)
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos,
                                    BlockState state, Direction face) {
        return face.getAxis() == getRotationAxis(state);
    }
}