/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.mcreator.createmixandclean.fluid.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<FlowingFluid> CHLORINE_GAS = REGISTRY.register("chlorine_gas", () -> new ChlorineGasFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_CHLORINE_GAS = REGISTRY.register("flowing_chlorine_gas", () -> new ChlorineGasFluid.Flowing());
	public static final RegistryObject<FlowingFluid> HYDROGEN_GAS = REGISTRY.register("hydrogen_gas", () -> new HydrogenGasFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_HYDROGEN_GAS = REGISTRY.register("flowing_hydrogen_gas", () -> new HydrogenGasFluid.Flowing());
	public static final RegistryObject<FlowingFluid> HYDROCHLORIC_ACID = REGISTRY.register("hydrochloric_acid", () -> new HydrochloricAcidFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_HYDROCHLORIC_ACID = REGISTRY.register("flowing_hydrochloric_acid", () -> new HydrochloricAcidFluid.Flowing());
	public static final RegistryObject<FlowingFluid> IRON_SLURRY = REGISTRY.register("iron_slurry", () -> new IronSlurryFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_IRON_SLURRY = REGISTRY.register("flowing_iron_slurry", () -> new IronSlurryFluid.Flowing());
	public static final RegistryObject<FlowingFluid> OXYGEN_GAS = REGISTRY.register("oxygen_gas", () -> new OxygenGasFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_OXYGEN_GAS = REGISTRY.register("flowing_oxygen_gas", () -> new OxygenGasFluid.Flowing());
	public static final RegistryObject<FlowingFluid> LIQUID_AIR = REGISTRY.register("liquid_air", () -> new LiquidAirFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_LIQUID_AIR = REGISTRY.register("flowing_liquid_air", () -> new LiquidAirFluid.Flowing());
	public static final RegistryObject<FlowingFluid> NITROGEN_GAS = REGISTRY.register("nitrogen_gas", () -> new NitrogenGasFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_NITROGEN_GAS = REGISTRY.register("flowing_nitrogen_gas", () -> new NitrogenGasFluid.Flowing());
	public static final RegistryObject<FlowingFluid> AMMONIA = REGISTRY.register("ammonia", () -> new AmmoniaFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_AMMONIA = REGISTRY.register("flowing_ammonia", () -> new AmmoniaFluid.Flowing());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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