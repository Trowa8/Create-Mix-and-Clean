
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.createmixandclean.block.HydrogenGasBlock;
import net.mcreator.createmixandclean.block.HydrochloricAcidBlock;
import net.mcreator.createmixandclean.block.ChlorineGasBlock;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<Block> CHLORINE_GAS = REGISTRY.register("chlorine_gas", () -> new ChlorineGasBlock());
	public static final RegistryObject<Block> HYDROGEN_GAS = REGISTRY.register("hydrogen_gas", () -> new HydrogenGasBlock());
	public static final RegistryObject<Block> HYDROCHLORIC_ACID = REGISTRY.register("hydrochloric_acid", () -> new HydrochloricAcidBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
