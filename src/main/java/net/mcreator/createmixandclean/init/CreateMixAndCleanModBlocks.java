/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.createmixandclean.block.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<Block> CHLORINE_GAS;
	public static final RegistryObject<Block> HYDROGEN_GAS;
	public static final RegistryObject<Block> HYDROCHLORIC_ACID;
	public static final RegistryObject<Block> ELECTROLYZER;
	public static final RegistryObject<Block> IRON_SLURRY;
	public static final RegistryObject<Block> OXYGEN_GAS;
	static {
		CHLORINE_GAS = REGISTRY.register("chlorine_gas", ChlorineGasBlock::new);
		HYDROGEN_GAS = REGISTRY.register("hydrogen_gas", HydrogenGasBlock::new);
		HYDROCHLORIC_ACID = REGISTRY.register("hydrochloric_acid", HydrochloricAcidBlock::new);
		ELECTROLYZER = REGISTRY.register("electrolyzer", ElectrolyzerBlock::new);
		IRON_SLURRY = REGISTRY.register("iron_slurry", IronSlurryBlock::new);
		OXYGEN_GAS = REGISTRY.register("oxygen_gas", OxygenGasBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}