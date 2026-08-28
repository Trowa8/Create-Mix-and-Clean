package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;
import net.mcreator.createmixandclean.gas.block.MixtureGasBlock;
import net.mcreator.createmixandclean.gas.block.entity.GasCellBlockEntity;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.mcreator.createmixandclean.gas.init.GasBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Map;

public final class GasCellMigrator {

    private GasCellMigrator() {}

    public static void migrateChunk(ServerLevel level, LevelChunk chunk) {
        boolean wantMixture = CreateMixAndCleanGasConfig.CELL_MODEL.get()
                == CreateMixAndCleanGasConfig.CellModel.MIXTURE;

        chunk.getBlockEntities().keySet().stream().toList().forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            boolean isMixture = state.getBlock() instanceof MixtureGasBlock;
            boolean isSingle = state.getBlock() instanceof DiffusingGasBlock;

            if (wantMixture && isSingle) {
                convertSingleToMixture(level, pos, state);
            } else if (!wantMixture && isMixture) {
                convertMixtureToSingle(level, pos);
            }
        });
    }

    private static void convertSingleToMixture(ServerLevel level, BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof DiffusingGasBlock gasBlock)) return;
        float amount = DiffusingGasBlock.levelOf(state);
        level.setBlock(pos, GasBlocks.MIXTURE_GAS.get().defaultBlockState(), 3);
        if (level.getBlockEntity(pos) instanceof GasCellBlockEntity be) {
            be.set(gasBlock.getGasType(), amount);
        }
    }

    private static void convertMixtureToSingle(ServerLevel level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof GasCellBlockEntity be)) return;
        GasType dominant = null;
        float best = -1f;
        for (Map.Entry<GasType, Float> entry : be.getAll().entrySet()) {
            if (entry.getValue() > best) {
                best = entry.getValue();
                dominant = entry.getKey();
            }
        }
        if (dominant == null) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            return;
        }
        DiffusingGasBlock block = GasBlockRegistryLookup.get(dominant);
        if (block == null) return;
        BlockState newState = block.defaultBlockState()
                .setValue(DiffusingGasBlock.LEVEL, Math.round(Math.min(15f, best)));
        level.setBlock(pos, newState, 3);
    }
}
