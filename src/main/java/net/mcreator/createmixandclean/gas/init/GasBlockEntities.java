package net.mcreator.createmixandclean.gas.init;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.gas.block.entity.GasCellBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GasBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateMixAndCleanMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GasCellBlockEntity>> MIXTURE_GAS_CELL =
        REGISTRY.register("mixture_gas_cell",
                    () -> BlockEntityType.Builder.of(GasCellBlockEntity::new, GasBlocks.MIXTURE_GAS.get()).build(null));
}
