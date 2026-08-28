package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluidTypes;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Supplier;

public enum GasType {
    CHLORINE(() -> CreateMixAndCleanModFluidTypes.CHLORINE_GAS_TYPE.get()),
    HYDROGEN(() -> CreateMixAndCleanModFluidTypes.HYDROGEN_GAS_TYPE.get()),
    OXYGEN(() -> CreateMixAndCleanModFluidTypes.OXYGEN_GAS_TYPE.get()),
    NITROGEN(() -> CreateMixAndCleanModFluidTypes.NITROGEN_GAS_TYPE.get()),
    AMMONIA(() -> CreateMixAndCleanModFluidTypes.AMMONIA_TYPE.get());

    private final Supplier<FluidType> fluidType;

    GasType(Supplier<FluidType> fluidType) {
        this.fluidType = fluidType;
    }

    public FluidType getFluidType() {
        return fluidType.get();
    }

    public static GasType byName(String name) {
        for (GasType type : values()) {
            if (type.name().equalsIgnoreCase(name)) return type;
        }
        return null;
    }
}
