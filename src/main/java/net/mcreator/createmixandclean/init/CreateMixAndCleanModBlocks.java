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
import net.mcreator.createmixandclean.block.ElectrolyzerBlock;
import net.mcreator.createmixandclean.block.ChlorineGasBlock;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<Block> CHLORINE_GAS;
	public static final RegistryObject<Block> HYDROGEN_GAS;
	public static final RegistryObject<Block> HYDROCHLORIC_ACID;
	public static final RegistryObject<Block> ELECTROLYZER;
	static {
		CHLORINE_GAS = REGISTRY.register("chlorine_gas", ChlorineGasBlock::new);
		HYDROGEN_GAS = REGISTRY.register("hydrogen_gas", HydrogenGasBlock::new);
		HYDROCHLORIC_ACID = REGISTRY.register("hydrochloric_acid", HydrochloricAcidBlock::new);
	// Start of user code block custom blocks
		ELECTROLYZER = REGISTRY.register("electrolyzer", () ->
    	new ElectrolyzerBlock(
       		net.minecraft.world.level.block.state.BlockBehaviour.Properties.of()
            	.strength(3.5f, 6f)
            	.requiresCorrectToolForDrops()
            	.sound(net.minecraft.world.level.block.SoundType.METAL)
    	)
		);
	// End of user code block custom blocks
	}

}