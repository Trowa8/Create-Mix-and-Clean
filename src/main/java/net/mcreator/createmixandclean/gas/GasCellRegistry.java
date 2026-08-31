package net.mcreator.createmixandclean.gas;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = "create_mix_and_clean")
public final class GasCellRegistry {
    private static final Map<LevelAccessor, Set<BlockPos>> ACTIVE = new HashMap<>();

    private GasCellRegistry() {}

    public static void add(LevelAccessor level, BlockPos pos) {
        ACTIVE.computeIfAbsent(level, l -> new HashSet<>()).add(pos.immutable());
    }

    public static void remove(LevelAccessor level, BlockPos pos) {
        Set<BlockPos> set = ACTIVE.get(level);
        if (set != null) set.remove(pos);
    }

    public static Set<BlockPos> get(LevelAccessor level) {
        return ACTIVE.getOrDefault(level, Set.of());
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        ACTIVE.remove(event.getLevel());
    }

    private static final Map<LevelAccessor, Set<BlockPos>> SUPPRESS_NEXT = new HashMap<>();
    
    public static void markSelfCleared(LevelAccessor level, BlockPos pos) {
        SUPPRESS_NEXT.computeIfAbsent(level, l -> new HashSet<>()).add(pos.immutable());
    }
    
    public static boolean consumeSelfCleared(LevelAccessor level, BlockPos pos) {
        Set<BlockPos> set = SUPPRESS_NEXT.get(level);
        return set != null && set.remove(pos.immutable());
    }
}