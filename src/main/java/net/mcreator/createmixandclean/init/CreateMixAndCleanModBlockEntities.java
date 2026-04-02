package main.java.net.mcreator.createmixandclean.init;

import net.mcreator.createmixandclean.block.ElectrolyzerBlock;
import net.mcreator.createmixandclean.blockentity.ElectrolyzerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,
                    CreateMixAndCleanMod.MODID);

    public static final RegistryObject<BlockEntityType<ElectrolyzerBlockEntity>> ELECTROLYZER =
            REGISTRY.register("electrolyzer", () ->
                    BlockEntityType.Builder
                            .of(ElectrolyzerBlockEntity::new,
                                    CreateMixAndCleanModBlocks.ELECTROLYZER.get())
                            .build(null));
}