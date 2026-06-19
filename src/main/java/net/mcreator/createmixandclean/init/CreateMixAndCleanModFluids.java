/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.mcreator.createmixandclean.fluid.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, CreateMixAndCleanMod.MODID);
	public static final DeferredHolder<Fluid, FlowingFluid> CHLORINE_GAS = REGISTRY.register("chlorine_gas", ChlorineGasFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_CHLORINE_GAS = REGISTRY.register("flowing_chlorine_gas", ChlorineGasFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> HYDROGEN_GAS = REGISTRY.register("hydrogen_gas", HydrogenGasFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_HYDROGEN_GAS = REGISTRY.register("flowing_hydrogen_gas", HydrogenGasFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> HYDROCHLORIC_ACID = REGISTRY.register("hydrochloric_acid", HydrochloricAcidFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_HYDROCHLORIC_ACID = REGISTRY.register("flowing_hydrochloric_acid", HydrochloricAcidFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> IRON_SLURRY = REGISTRY.register("iron_slurry", IronSlurryFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_IRON_SLURRY = REGISTRY.register("flowing_iron_slurry", IronSlurryFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> OXYGEN_GAS = REGISTRY.register("oxygen_gas", OxygenGasFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_OXYGEN_GAS = REGISTRY.register("flowing_oxygen_gas", OxygenGasFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> LIQUID_AIR = REGISTRY.register("liquid_air", LiquidAirFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_LIQUID_AIR = REGISTRY.register("flowing_liquid_air", LiquidAirFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> NITROGEN_GAS = REGISTRY.register("nitrogen_gas", NitrogenGasFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_NITROGEN_GAS = REGISTRY.register("flowing_nitrogen_gas", NitrogenGasFluid.Flowing::new);
	public static final DeferredHolder<Fluid, FlowingFluid> AMMONIA = REGISTRY.register("ammonia", AmmoniaFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_AMMONIA = REGISTRY.register("flowing_ammonia", AmmoniaFluid.Flowing::new);

	@EventBusSubscriber(Dist.CLIENT)
	public static class FluidsClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(CHLORINE_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_CHLORINE_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(HYDROGEN_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_HYDROGEN_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(HYDROCHLORIC_ACID.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_HYDROCHLORIC_ACID.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(IRON_SLURRY.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_IRON_SLURRY.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(OXYGEN_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_OXYGEN_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(LIQUID_AIR.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_LIQUID_AIR.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(NITROGEN_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_NITROGEN_GAS.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(AMMONIA.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_AMMONIA.get(), RenderType.translucent());
		}
	}
}