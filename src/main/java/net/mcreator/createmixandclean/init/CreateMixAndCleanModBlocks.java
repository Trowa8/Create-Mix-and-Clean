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
	public static final RegistryObject<Block> BAUXITE;
	public static final RegistryObject<Block> ALUMINUM_BLOCK;
	public static final RegistryObject<Block> ALUMINUM_DOOR;
	public static final RegistryObject<Block> ALUMINUM_TRAPDOOR;
	public static final RegistryObject<Block> LIQUID_AIR;
	public static final RegistryObject<Block> NITROGEN_GAS;
	public static final RegistryObject<Block> AMMONIA;
	static {
		CHLORINE_GAS = REGISTRY.register("chlorine_gas", ChlorineGasBlock::new);
		HYDROGEN_GAS = REGISTRY.register("hydrogen_gas", HydrogenGasBlock::new);
		HYDROCHLORIC_ACID = REGISTRY.register("hydrochloric_acid", HydrochloricAcidBlock::new);
		ELECTROLYZER = REGISTRY.register("electrolyzer", ElectrolyzerBlock::new);
		IRON_SLURRY = REGISTRY.register("iron_slurry", IronSlurryBlock::new);
		OXYGEN_GAS = REGISTRY.register("oxygen_gas", OxygenGasBlock::new);
		BAUXITE = REGISTRY.register("bauxite", BauxiteBlock::new);
		ALUMINUM_BLOCK = REGISTRY.register("aluminum_block", AluminumBlockBlock::new);
		ALUMINUM_DOOR = REGISTRY.register("aluminum_door", AluminumDoorBlock::new);
		ALUMINUM_TRAPDOOR = REGISTRY.register("aluminum_trapdoor", AluminumTrapdoorBlock::new);
		LIQUID_AIR = REGISTRY.register("liquid_air", LiquidAirBlock::new);
		NITROGEN_GAS = REGISTRY.register("nitrogen_gas", NitrogenGasBlock::new);
		AMMONIA = REGISTRY.register("ammonia", AmmoniaBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}