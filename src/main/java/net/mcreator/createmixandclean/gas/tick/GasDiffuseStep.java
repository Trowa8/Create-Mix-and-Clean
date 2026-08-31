package net.mcreator.createmixandclean.gas.tick;

import net.mcreator.createmixandclean.gas.GasCellAccess;
import net.mcreator.createmixandclean.gas.GasCellFactory;
import net.mcreator.createmixandclean.gas.GasCellRegistry;
import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.config.CreateMixAndCleanGasConfig;
import net.mcreator.createmixandclean.gas.pocket.GasPocketManager;
import net.mcreator.createmixandclean.gas.wind.GasWindHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public final class GasDiffuseStep {

    private GasDiffuseStep() {}
    
    private static final float PURGE_THRESHOLD = 0.005f; 

    public static void run(Level level, BlockPos pos) {
        GasCellAccess self = GasCellFactory.at(level, pos);
        if (self == null || self.isEmpty()) return;
    
        boolean changed = false;
        Set<GasType> gases = new HashSet<>(self.getPresentGases());
        Direction windBias = CreateMixAndCleanGasConfig.WIND_AWARENESS.get()
                ? GasWindHelper.getBias(level, pos)
                : null;
    
        for (GasType gas : gases) {
            float myConc = self.getConcentration(gas);

            if (myConc <= PURGE_THRESHOLD) {
                self.setConcentration(gas, 0f);
                changed = true;
                continue;
            }
    
            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                GasCellAccess neighbor = GasCellFactory.at(level, neighborPos);
                if (neighbor == null) continue;
    
                float neighborConc = neighbor.getConcentration(gas);
                if (neighborConc >= myConc) continue;
    
                float weight = 0.125f;
                if (windBias != null && dir == windBias) weight *= 3f;
    
                float transfer = (myConc - neighborConc) * weight;
                if (transfer <= 0.0001f) continue;
    
                self.setConcentration(gas, myConc - transfer);
                neighbor.setConcentration(gas, neighborConc + transfer);
                myConc -= transfer;
                changed = true;
    
                if (level instanceof ServerLevel serverLevel) {
                    Set<BlockPos> registry = GasCellRegistry.get(serverLevel);
                    if (registry != null) {
                        registry.add(neighborPos.immutable());
                    }
                    GasTickScheduler.wake(serverLevel, neighborPos);
                }
            }
        }
    
        if (CreateMixAndCleanGasConfig.OUTDOOR_DISSIPATION.get()) {
            changed |= applyOutdoorDecay(level, pos, self);
        }

        if (self.isEmpty() || self.getTotalLevel() <= PURGE_THRESHOLD) {
            GasCellRegistry.markSelfCleared(level, pos);
            self.clear();
            if (level instanceof ServerLevel serverLevel) {
                Set<BlockPos> registry = GasCellRegistry.get(serverLevel);
                if (registry != null) {
                    registry.remove(pos.immutable());
                }
                GasPocketManager.get(level).markDirty(pos);
            }
        } else if (changed && level instanceof ServerLevel serverLevel) {
            GasPocketManager.get(level).markDirty(pos);
            GasTickScheduler.enqueue(serverLevel, pos);
        }
    }

    private static boolean applyOutdoorDecay(Level level, BlockPos pos, GasCellAccess self) {
        if (!level.canSeeSky(pos)) return false;
        float rate = CreateMixAndCleanGasConfig.OUTDOOR_DECAY_RATE.get().floatValue();
        if (rate <= 0f) return false;
        boolean changed = false;
        for (GasType gas : self.getPresentGases()) {
            float current = self.getConcentration(gas);
            if (current > 0f) {
                float next = Math.max(0f, current - rate);
                self.setConcentration(gas, next);
                changed = true;
            }
        }
        return changed;
    }
}
