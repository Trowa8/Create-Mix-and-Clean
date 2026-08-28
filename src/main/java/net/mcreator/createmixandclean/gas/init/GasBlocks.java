package net.mcreator.createmixandclean.gas.init;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.gas.GasBlockRegistryLookup;
import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.block.DiffusingGasBlock;
import net.mcreator.createmixandclean.gas.block.MixtureGasBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GasBlocks {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(CreateMixAndCleanMod.MODID);

    public static final DeferredBlock<MixtureGasBlock> MIXTURE_GAS =
            REGISTRY.register("mixture_gas_cell", MixtureGasBlock::new);

    public static final DeferredBlock<DiffusingGasBlock> CHLORINE_CELL =
            REGISTRY.register("chlorine_gas_cell", () -> new DiffusingGasBlock(GasType.CHLORINE));
    public static final DeferredBlock<DiffusingGasBlock> HYDROGEN_CELL =
            REGISTRY.register("hydrogen_gas_cell", () -> new DiffusingGasBlock(GasType.HYDROGEN));
    public static final DeferredBlock<DiffusingGasBlock> OXYGEN_CELL =
            REGISTRY.register("oxygen_gas_cell", () -> new DiffusingGasBlock(GasType.OXYGEN));
    public static final DeferredBlock<DiffusingGasBlock> NITROGEN_CELL =
            REGISTRY.register("nitrogen_gas_cell", () -> new DiffusingGasBlock(GasType.NITROGEN));
    public static final DeferredBlock<DiffusingGasBlock> AMMONIA_CELL =
            REGISTRY.register("ammonia_gas_cell", () -> new DiffusingGasBlock(GasType.AMMONIA));

    public static void bindLookups() {
        GasBlockRegistryLookup.register(GasType.CHLORINE, CHLORINE_CELL.get());
        GasBlockRegistryLookup.register(GasType.HYDROGEN, HYDROGEN_CELL.get());
        GasBlockRegistryLookup.register(GasType.OXYGEN, OXYGEN_CELL.get());
        GasBlockRegistryLookup.register(GasType.NITROGEN, NITROGEN_CELL.get());
        GasBlockRegistryLookup.register(GasType.AMMONIA, AMMONIA_CELL.get());
    }
}
