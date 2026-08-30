package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;
import net.mcreator.createmixandclean.gas.block.MixtureGasBlock;
import net.mcreator.createmixandclean.gas.block.entity.GasCellBlockEntity;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.mcreator.createmixandclean.gas.init.GasBlocks;
import net.mcreator.createmixandclean.gas.tick.GasTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

import java.util.Map;

@EventBusSubscriber(modid = "create_mix_and_clean")
public final class GasCellMigrator {

    private GasCellMigrator() {}

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;
        if (event.getLevel() instanceof ServerLevel level && event.getChunk() instanceof LevelChunk chunk) {
            migrateChunk(level, chunk);
            rewakeGasCells(level, chunk);
        }
    }
    
    private static void rewakeGasCells(ServerLevel level, LevelChunk chunk) {
        chunk.getBlockEntities().forEach((pos, be) -> {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof MixtureGasBlock || state.getBlock() instanceof DiffusingGasBlock) {
                GasCellRegistry.add(level, pos);
                GasTickScheduler.wake(level, pos);
            }
        });
    }

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
        level.setBlock(pos, GasBlocks.MIXTURE_GAS.get().defaultBlockState(), 2);
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
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            return;
        }
        DiffusingGasBlock block = GasRegistry.getBlock(dominant);
        if (block == null) return;
        BlockState newState = block.defaultBlockState()
                .setValue(DiffusingGasBlock.LEVEL, Math.round(Math.min(15f, best)));
        level.setBlock(pos, newState, 2);
    }
}