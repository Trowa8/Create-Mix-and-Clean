/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.fluids.FluidType;

import net.mcreator.createmixandclean.fluid.types.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, CreateMixAndCleanMod.MODID);
	public static final DeferredHolder<FluidType, FluidType> CHLORINE_GAS_TYPE = REGISTRY.register("chlorine_gas", ChlorineGasFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> HYDROGEN_GAS_TYPE = REGISTRY.register("hydrogen_gas", HydrogenGasFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> HYDROCHLORIC_ACID_TYPE = REGISTRY.register("hydrochloric_acid", HydrochloricAcidFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> IRON_SLURRY_TYPE = REGISTRY.register("iron_slurry", IronSlurryFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> OXYGEN_GAS_TYPE = REGISTRY.register("oxygen_gas", OxygenGasFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> LIQUID_AIR_TYPE = REGISTRY.register("liquid_air", LiquidAirFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> NITROGEN_GAS_TYPE = REGISTRY.register("nitrogen_gas", NitrogenGasFluidType::new);
	public static final DeferredHolder<FluidType, FluidType> AMMONIA_TYPE = REGISTRY.register("ammonia", AmmoniaFluidType::new);
}