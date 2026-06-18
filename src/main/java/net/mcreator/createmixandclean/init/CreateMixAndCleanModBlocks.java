/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.createmixandclean.block.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(CreateMixAndCleanMod.MODID);
	public static final DeferredBlock<Block> CHLORINE_GAS;
	public static final DeferredBlock<Block> HYDROGEN_GAS;
	public static final DeferredBlock<Block> HYDROCHLORIC_ACID;
	public static final DeferredBlock<Block> ELECTROLYZER;
	public static final DeferredBlock<Block> IRON_SLURRY;
	public static final DeferredBlock<Block> OXYGEN_GAS;
	public static final DeferredBlock<Block> BAUXITE;
	public static final DeferredBlock<Block> ALUMINUM_BLOCK;
	public static final DeferredBlock<Block> ALUMINUM_DOOR;
	public static final DeferredBlock<Block> ALUMINUM_TRAPDOOR;
	public static final DeferredBlock<Block> LIQUID_AIR;
	public static final DeferredBlock<Block> NITROGEN_GAS;
	public static final DeferredBlock<Block> AMMONIA;
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