package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluidTypes;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Supplier;

public enum GasType {
    CHLORINE(() -> CreateMixAndCleanModFluidTypes.CHLORINE_GAS_TYPE.get(), 0xC8CC32),
    HYDROGEN(() -> CreateMixAndCleanModFluidTypes.HYDROGEN_GAS_TYPE.get(), 0xE8E8E8),
    OXYGEN(() -> CreateMixAndCleanModFluidTypes.OXYGEN_GAS_TYPE.get(), 0x9FD8FF),
    NITROGEN(() -> CreateMixAndCleanModFluidTypes.NITROGEN_GAS_TYPE.get(), 0xC7C7FF),
    AMMONIA(() -> CreateMixAndCleanModFluidTypes.AMMONIA_TYPE.get(), 0x3597330f);

    private final Supplier<FluidType> fluidType;
    private final int color;

    GasType(Supplier<FluidType> fluidType, int color) {
        this.fluidType = fluidType;
        this.color = color;
    }

    public FluidType getFluidType() {
        return fluidType.get();
    }

    public float getRed() {
        return ((color >> 16) & 0xFF) / 255f;
    }

    public float getGreen() {
        return ((color >> 8) & 0xFF) / 255f;
    }

    public float getBlue() {
        return (color & 0xFF) / 255f;
    }

    public float[] getFallbackColor() {
        return new float[]{getRed(), getGreen(), getBlue()};
    }

    public static GasType byName(String name) {
        for (GasType type : values()) {
            if (type.name().equalsIgnoreCase(name)) return type;
        }
        return null;
    }
}
