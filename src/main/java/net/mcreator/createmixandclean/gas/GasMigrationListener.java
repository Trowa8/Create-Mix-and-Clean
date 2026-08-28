package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class GasMigrationListener {

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!CreateMixAndCleanGasConfig.MASTER_ENABLED.get()) return;
        if (event.getLevel() instanceof ServerLevel level && event.getChunk() instanceof LevelChunk chunk) {
            GasCellMigrator.migrateChunk(level, chunk);
        }
    }
}
