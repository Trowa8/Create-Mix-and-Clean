package net.mcreator.createmixandclean.gas;

import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;

import java.util.EnumMap;
import java.util.Map;

public final class GasBlockRegistryLookup {

    private static final Map<GasType, DiffusingGasBlock> REGISTRY = new EnumMap<>(GasType.class);

    private GasBlockRegistryLookup() {}

    public static void register(GasType type, DiffusingGasBlock block) {
        REGISTRY.put(type, block);
    }

    public static DiffusingGasBlock get(GasType type) {
        return REGISTRY.get(type);
    }
}
