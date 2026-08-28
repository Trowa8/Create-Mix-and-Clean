package net.mcreator.createmixandclean.gas.block;

import net.mcreator.createmixandclean.gas.GasType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DiffusingGasBlock extends Block {

    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 15);

    private final GasType gasType;

    public DiffusingGasBlock(GasType gasType) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .noCollission()
                .noLootTable()
                .replaceable()
                .sound(SoundType.EMPTY)
                .pushReaction(PushReaction.DESTROY)
                .strength(-1f, 3600000f));
        this.gasType = gasType;
        registerDefaultState(stateDefinition.any().setValue(LEVEL, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public GasType getGasType() {
        return gasType;
    }

    public static int levelOf(BlockState state) {
        return state.getValue(LEVEL);
    }
}