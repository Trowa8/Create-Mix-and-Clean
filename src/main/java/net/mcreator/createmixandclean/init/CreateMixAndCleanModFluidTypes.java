/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;

import net.mcreator.createmixandclean.fluid.types.HydrogenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.HydrochloricAcidFluidType;
import net.mcreator.createmixandclean.fluid.types.ChlorineGasFluidType;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<FluidType> CHLORINE_GAS_TYPE = REGISTRY.register("chlorine_gas", () -> new ChlorineGasFluidType());
	public static final RegistryObject<FluidType> HYDROGEN_GAS_TYPE = REGISTRY.register("hydrogen_gas", () -> new HydrogenGasFluidType());
	public static final RegistryObject<FluidType> HYDROCHLORIC_ACID_TYPE = REGISTRY.register("hydrochloric_acid", () -> new HydrochloricAcidFluidType());
}