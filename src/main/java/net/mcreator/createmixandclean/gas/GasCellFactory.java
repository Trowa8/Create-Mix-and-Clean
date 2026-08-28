package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;
import net.mcreator.createmixandclean.gas.block.MixtureGasBlock;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class GasCellFactory {

    private GasCellFactory() {}

    public static GasCellAccess at(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof MixtureGasBlock) {
            return new MixtureGasCellAccess(level, pos);
        }
        if (state.getBlock() instanceof DiffusingGasBlock) {
            return new SingleGasCellAccess(level, pos);
        }
        if (state.isAir()) {
            return CreateMixAndCleanGasConfig.CELL_MODEL.get() == CreateMixAndCleanGasConfig.CellModel.MIXTURE
                    ? new MixtureGasCellAccess(level, pos)
                    : new SingleGasCellAccess(level, pos);
        }
        return null;
    }
}
