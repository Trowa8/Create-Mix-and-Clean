package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;
import net.minecraft.world.level.material.Fluid;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class GasRegistry {

    private static final Map<Fluid, GasType> BY_FLUID = new HashMap<>();
    private static final Map<GasType, DiffusingGasBlock> BY_TYPE = new EnumMap<>(GasType.class);

    private GasRegistry() {}

    public static void init() {
        registerFluid(CreateMixAndCleanModFluids.CHLORINE_GAS.get(), GasType.CHLORINE);
        registerFluid(CreateMixAndCleanModFluids.FLOWING_CHLORINE_GAS.get(), GasType.CHLORINE);
        registerFluid(CreateMixAndCleanModFluids.HYDROGEN_GAS.get(), GasType.HYDROGEN);
        registerFluid(CreateMixAndCleanModFluids.FLOWING_HYDROGEN_GAS.get(), GasType.HYDROGEN);
        registerFluid(CreateMixAndCleanModFluids.OXYGEN_GAS.get(), GasType.OXYGEN);
        registerFluid(CreateMixAndCleanModFluids.FLOWING_OXYGEN_GAS.get(), GasType.OXYGEN);
        registerFluid(CreateMixAndCleanModFluids.NITROGEN_GAS.get(), GasType.NITROGEN);
        registerFluid(CreateMixAndCleanModFluids.FLOWING_NITROGEN_GAS.get(), GasType.NITROGEN);
        registerFluid(CreateMixAndCleanModFluids.AMMONIA.get(), GasType.AMMONIA);
        registerFluid(CreateMixAndCleanModFluids.FLOWING_AMMONIA.get(), GasType.AMMONIA);
    }

    private static void registerFluid(Fluid fluid, GasType type) {
        BY_FLUID.put(fluid, type);
    }

    public static GasType getGasType(Fluid fluid) {
        return BY_FLUID.get(fluid);
    }

    public static void registerBlock(GasType type, DiffusingGasBlock block) {
        BY_TYPE.put(type, block);
    }

    public static DiffusingGasBlock getBlock(GasType type) {
        return BY_TYPE.get(type);
    }
}