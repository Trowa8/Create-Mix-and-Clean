package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.MixtureGasBlock;
import net.mcreator.createmixandclean.gas.block.entity.GasCellBlockEntity;
import net.mcreator.createmixandclean.gas.init.GasBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.Set;

public class MixtureGasCellAccess implements GasCellAccess {

    private final Level level;
    private final BlockPos pos;
    private static final float PURGE_THRESHOLD = 0.001f;

    public MixtureGasCellAccess(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    private GasCellBlockEntity be() {
        return level.getBlockEntity(pos) instanceof GasCellBlockEntity gbe ? gbe : null;
    }

    @Override
    public float getConcentration(GasType gasType) {
        GasCellBlockEntity be = be();
        return be == null ? 0f : be.get(gasType);
    }

    @Override
    public void setConcentration(GasType gasType, float value) {
        BlockState state = level.getBlockState(pos);
        float sanitizedValue = value <= PURGE_THRESHOLD ? 0f : value;

        if (!(state.getBlock() instanceof MixtureGasBlock)) {
            if (sanitizedValue <= 0f) return;
            if (!state.isAir()) return;
            level.setBlock(pos, GasBlocks.MIXTURE_GAS.get().defaultBlockState(), 3);
        }

        GasCellBlockEntity be = be();
        if (be != null) {
            be.set(gasType, sanitizedValue);
            
            if (be.isEmpty() || getTotalLevel() <= PURGE_THRESHOLD) {
                clear();
            } else {
                GasCellRegistry.add(level, pos);
            }
        }
    }

    @Override
    public Set<GasType> getPresentGases() {
        GasCellBlockEntity be = be();
        if (be == null || be.getAll().isEmpty()) return EnumSet.noneOf(GasType.class);
        return EnumSet.copyOf(be.getAll().keySet());
    }

    @Override
    public float getTotalLevel() {
        GasCellBlockEntity be = be();
        if (be == null) return 0f;
        float total = 0f;
        for (float v : be.getAll().values()) total += v;
        return total;
    }

    @Override
    public boolean isEmpty() {
        return getTotalLevel() <= PURGE_THRESHOLD;
    }

    @Override
    public void clear() {
        GasCellBlockEntity be = be();
        if (be != null) {
            be.clear();
        }
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        GasCellRegistry.remove(level, pos);
    }
}
