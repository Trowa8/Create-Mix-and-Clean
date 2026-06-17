/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;

import net.mcreator.createmixandclean.fluid.types.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<FluidType> CHLORINE_GAS_TYPE = REGISTRY.register("chlorine_gas", () -> new ChlorineGasFluidType());
	public static final RegistryObject<FluidType> HYDROGEN_GAS_TYPE = REGISTRY.register("hydrogen_gas", () -> new HydrogenGasFluidType());
	public static final RegistryObject<FluidType> HYDROCHLORIC_ACID_TYPE = REGISTRY.register("hydrochloric_acid", () -> new HydrochloricAcidFluidType());
	public static final RegistryObject<FluidType> IRON_SLURRY_TYPE = REGISTRY.register("iron_slurry", () -> new IronSlurryFluidType());
	public static final RegistryObject<FluidType> OXYGEN_GAS_TYPE = REGISTRY.register("oxygen_gas", () -> new OxygenGasFluidType());
	public static final RegistryObject<FluidType> LIQUID_AIR_TYPE = REGISTRY.register("liquid_air", () -> new LiquidAirFluidType());
	public static final RegistryObject<FluidType> NITROGEN_GAS_TYPE = REGISTRY.register("nitrogen_gas", () -> new NitrogenGasFluidType());
	public static final RegistryObject<FluidType> AMMONIA_TYPE = REGISTRY.register("ammonia", () -> new AmmoniaFluidType());
}