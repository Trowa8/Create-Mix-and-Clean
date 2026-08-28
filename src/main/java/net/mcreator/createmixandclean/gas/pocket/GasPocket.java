package net.mcreator.createmixandclean.gas.pocket;

import net.mcreator.createmixandclean.gas.GasType;
import net.minecraft.core.BlockPos;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GasPocket {

    public final Set<BlockPos> cells = new HashSet<>();
    public final Map<GasType, Float> composition = new EnumMap<>(GasType.class);
    public boolean skyExposed = false;
    public boolean settled = false;
    public long lastRecomputeTick = 0L;
    public int stableStreak = 0;
    public boolean capped = false;

    public int cellCount() {
        return cells.size();
    }
}
