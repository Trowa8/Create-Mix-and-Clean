package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public final class GasFluidLookup {

    private static final Map<Fluid, GasType> BY_FLUID = new HashMap<>();

    private GasFluidLookup() {}

    public static void init() {
        register(CreateMixAndCleanModFluids.CHLORINE_GAS.get(), GasType.CHLORINE);
        register(CreateMixAndCleanModFluids.FLOWING_CHLORINE_GAS.get(), GasType.CHLORINE);
        register(CreateMixAndCleanModFluids.HYDROGEN_GAS.get(), GasType.HYDROGEN);
        register(CreateMixAndCleanModFluids.FLOWING_HYDROGEN_GAS.get(), GasType.HYDROGEN);
        register(CreateMixAndCleanModFluids.OXYGEN_GAS.get(), GasType.OXYGEN);
        register(CreateMixAndCleanModFluids.FLOWING_OXYGEN_GAS.get(), GasType.OXYGEN);
        register(CreateMixAndCleanModFluids.NITROGEN_GAS.get(), GasType.NITROGEN);
        register(CreateMixAndCleanModFluids.FLOWING_NITROGEN_GAS.get(), GasType.NITROGEN);
        register(CreateMixAndCleanModFluids.AMMONIA.get(), GasType.AMMONIA);
        register(CreateMixAndCleanModFluids.FLOWING_AMMONIA.get(), GasType.AMMONIA);
    }

    private static void register(Fluid fluid, GasType type) {
        BY_FLUID.put(fluid, type);
    }

    public static GasType get(Fluid fluid) {
        return BY_FLUID.get(fluid);
    }
}
