package net.mcreator.createmixandclean.gas.wind;

import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class GasWindHelper {

    private static final int SEARCH_RADIUS = 5;

    private GasWindHelper() {}

    public static Direction getBias(Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            for (int distance = 1; distance <= SEARCH_RADIUS; distance++) {
                BlockPos check = pos.relative(dir.getOpposite(), distance);
                BlockEntity be = level.getBlockEntity(check);
                if (be instanceof IAirCurrentSource source) {
                    AirCurrent current = source.getAirCurrent();
                    if (current != null && current.direction == dir && withinCurrent(current, pos)) {
                        return dir;
                    }
                }
            }
        }
        return null;
    }

    private static boolean withinCurrent(AirCurrent current, BlockPos pos) {
        return current.bounds != null
            && current.bounds.contains(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }
}
