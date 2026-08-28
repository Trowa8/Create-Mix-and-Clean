package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.Set;

public class SingleGasCellAccess implements GasCellAccess {

    private final LevelAccessor level;
    private final BlockPos pos;

    public SingleGasCellAccess(LevelAccessor level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    @Override
    public float getConcentration(GasType gasType) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof DiffusingGasBlock gasBlock && gasBlock.getGasType() == gasType) {
            return DiffusingGasBlock.levelOf(state);
        }
        return 0f;
    }

    @Override
    public void setConcentration(GasType gasType, float value) {
        BlockState state = level.getBlockState(pos);
        int clamped = Math.max(0, Math.min(15, Math.round(value)));

        if (state.getBlock() instanceof DiffusingGasBlock gasBlock) {
            if (gasBlock.getGasType() != gasType) {
                if (clamped > DiffusingGasBlock.levelOf(state)) {
                    placeGas(gasType, clamped);
                }
                return;
            }
            if (clamped <= 0) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            } else {
                level.setBlock(pos, state.setValue(DiffusingGasBlock.LEVEL, clamped), 3);
            }
            return;
        }

        if (clamped > 0 && state.isAir()) {
            placeGas(gasType, clamped);
        }
    }

    private void placeGas(GasType gasType, int amount) {
        DiffusingGasBlock block = GasBlockRegistryLookup.get(gasType);
        if (block == null) return;
        BlockState newState = block.defaultBlockState().setValue(DiffusingGasBlock.LEVEL, amount);
        level.setBlock(pos, newState, 3);
    }

    @Override
    public Set<GasType> getPresentGases() {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof DiffusingGasBlock gasBlock && DiffusingGasBlock.levelOf(state) > 0) {
            return EnumSet.of(gasBlock.getGasType());
        }
        return EnumSet.noneOf(GasType.class);
    }

    @Override
    public float getTotalLevel() {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof DiffusingGasBlock ? DiffusingGasBlock.levelOf(state) : 0f;
    }

    @Override
    public boolean isEmpty() {
        return getTotalLevel() <= 0f;
    }

    @Override
    public void clear() {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }
}
