package net.mcreator.createmixandclean;

import net.mcreator.createmixandclean.fluid.types.AmmoniaFluidType;
import net.mcreator.createmixandclean.fluid.types.HydrogenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.OxygenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.NitrogenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.ChlorineGasFluidType;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModItems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidStack;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.systems.RenderSystem;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "create_mix_and_clean")
public class HazardHelmetHandler {

    private static final int MAX_FILTER_DURABILITY = 120;
    private static final String FILTER_NBT = "FilterDurability";
    private static final Set<UUID> wasInGas = new HashSet<>();
    private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("create_mix_and_clean", "textures/screens/goggle_vignette.png");
    private static final ResourceLocation GOGGLE_SHADER = new ResourceLocation("create_mix_and_clean", "shaders/post/goggle_effect.json");
	private static boolean goggleShaderActive = false;

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.is(CreateMixAndCleanModItems.HAZARD_PROTECTION_HELMET.get())) return;
        helmet.getOrCreateTag().putBoolean("Unbreakable", true);

        FluidState eyeFluid = player.level().getFluidState(BlockPos.containing(player.getEyePosition()));
        boolean inGas = isFilteredGas(eyeFluid);
        UUID id = player.getUUID();

        if (inGas) {
            int durability = getFilterDurability(helmet);

            if (!wasInGas.contains(id)) {
                wasInGas.add(id);
                player.displayClientMessage(Component.literal(durabilityColor(durability) + "Filter: " + durability + "/" + MAX_FILTER_DURABILITY), true);
            }

            if (durability > 0) {
                player.setAirSupply(player.getMaxAirSupply());

                if (player.tickCount % 20 == 0) {
                    int newDurability = durability - 1;
                    setFilterDurability(helmet, newDurability);
                    if (newDurability == 0) {
                        player.displayClientMessage(Component.literal("§cFilter depleted"), true);
                    } else {
                        player.displayClientMessage(Component.literal(durabilityColor(newDurability) + "Filter: " + newDurability + "/" + MAX_FILTER_DURABILITY), true);
                    }
                }
            }
        } else {
            wasInGas.remove(id);
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.is(CreateMixAndCleanModItems.HAZARD_PROTECTION_HELMET.get())) return;

        ItemStack held = player.getMainHandItem();
        if (!held.is(CreateMixAndCleanModItems.GASMASK_FILTER.get())) return;

        int oldDurability = getFilterDurability(helmet);
        int newDurability = held.hasTag() && held.getTag().contains(FILTER_NBT)
            ? held.getTag().getInt(FILTER_NBT)
            : MAX_FILTER_DURABILITY;

        ItemStack oldFilter = new ItemStack(CreateMixAndCleanModItems.GASMASK_FILTER.get());
        oldFilter.getOrCreateTag().putInt(FILTER_NBT, oldDurability);
        player.addItem(oldFilter);

        setFilterDurability(helmet, newDurability);
        held.shrink(1);

        player.displayClientMessage(Component.literal("Filter replaced. " + durabilityColor(newDurability) + "Durability: " + newDurability + "/" + MAX_FILTER_DURABILITY), true);
        event.setCanceled(true);
    }

    public static boolean isProtected(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.is(CreateMixAndCleanModItems.HAZARD_PROTECTION_HELMET.get())) return false;
        return getFilterDurability(helmet) > 0;
    }

    public static boolean isWearingProtectiveGear() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        ItemStack helmet = mc.player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return false;
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(helmet.getItem());
        if (key == null) return false;
        String path = key.getPath();
        return path.equals("ogi_helmet") || path.equals("hazard_protection_helmet");
    }

    private static boolean shouldApplyNightVision() {
    	Minecraft mc = Minecraft.getInstance();
    	if (mc.player == null) return false;
    	ItemStack helmet = mc.player.getItemBySlot(EquipmentSlot.HEAD);
    	if (helmet.isEmpty()) return false;
    	ResourceLocation key = ForgeRegistries.ITEMS.getKey(helmet.getItem());
    	if (key == null) return false;
    	String path = key.getPath();
    	return path.equals("hazard_protection_helmet");
	}

    private static boolean isFilteredGas(FluidState state) {
        return state.getFluidType() instanceof AmmoniaFluidType
            || state.getFluidType() instanceof HydrogenGasFluidType
            || state.getFluidType() instanceof OxygenGasFluidType
            || state.getFluidType() instanceof NitrogenGasFluidType
            || state.getFluidType() instanceof ChlorineGasFluidType;
    }

    private static int getFilterDurability(ItemStack helmet) {
        if (!helmet.hasTag() || !helmet.getTag().contains(FILTER_NBT)) return MAX_FILTER_DURABILITY;
        return helmet.getTag().getInt(FILTER_NBT);
    }

    private static void setFilterDurability(ItemStack helmet, int value) {
        helmet.getOrCreateTag().putInt(FILTER_NBT, Math.max(0, value));
    }

    private static String durabilityColor(int dur) {
        return dur > 40 ? "§a" : dur > 20 ? "§e" : "§c";
    }

    @Mod.EventBusSubscriber(modid = "create_mix_and_clean", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModSetup {

        private static final List<String> GAS_NAMES = Arrays.asList(
            "ammonia"
        );

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                try {
                    Field renderPropsField = FluidType.class.getDeclaredField("renderProperties");
                    renderPropsField.setAccessible(true);

                    for (FluidType fluidType : ForgeRegistries.FLUID_TYPES.get()) {
                        ResourceLocation registryName = ForgeRegistries.FLUID_TYPES.get().getKey(fluidType);
                        if (registryName == null) continue;

                        String namespace = registryName.getNamespace();
                        String path = registryName.getPath();

                        if ((namespace.equals("create_mix_and_clean"))
                                && GAS_NAMES.contains(path)) {

                            Object originalProps = renderPropsField.get(fluidType);
                            if (!(originalProps instanceof IClientFluidTypeExtensions originalExtensions)) continue;

                            renderPropsField.set(fluidType, new IClientFluidTypeExtensions() {
                                @Override
                                public ResourceLocation getStillTexture() {
                                    return originalExtensions.getStillTexture();
                                }

                                @Override
                                public ResourceLocation getFlowingTexture() {
                                    return originalExtensions.getFlowingTexture();
                                }

                                @Override
                                public ResourceLocation getStillTexture(FluidState state, BlockAndTintGetter world, BlockPos pos) {
                                    if (isWearingProtectiveGear())
                                        return new ResourceLocation(namespace + ":block/ogi_gas_still");
                                    return originalExtensions.getStillTexture(state, world, pos);
                                }

                                @Override
                                public ResourceLocation getFlowingTexture(FluidState state, BlockAndTintGetter world, BlockPos pos) {
                                    if (isWearingProtectiveGear())
                                        return new ResourceLocation(namespace + ":block/ogi_gas_flow");
                                    return originalExtensions.getFlowingTexture(state, world, pos);
                                }

                                @Override
                                public int getTintColor() {
                                    if (isWearingProtectiveGear())
                                        return (255 << 24) | (230 << 16) | (230 << 8) | 230;
                                    return originalExtensions.getTintColor();
                                }

                                @Override
                                public int getTintColor(FluidStack stack) {
                                    if (isWearingProtectiveGear())
                                        return (255 << 24) | (230 << 16) | (230 << 8) | 230;
                                    return originalExtensions.getTintColor(stack);
                                }

                                @Override
                                public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
                                    if (isWearingProtectiveGear())
                                        return (255 << 24) | (230 << 16) | (230 << 8) | 230;
                                    return originalExtensions.getTintColor(state, world, pos);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Mod.EventBusSubscriber(modid = "create_mix_and_clean", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientForgeEvents {

    	private static boolean wasWearingProtection = false;
    	private static java.lang.reflect.Field POST_EFFECT_FIELD;
    	private static java.lang.reflect.Field PASSES_FIELD;

    	static {
        	try {
            	POST_EFFECT_FIELD = net.minecraft.client.renderer.GameRenderer.class.getDeclaredField("postEffect");
            	POST_EFFECT_FIELD.setAccessible(true);
            	PASSES_FIELD = net.minecraft.client.renderer.PostChain.class.getDeclaredField("passes");
            	PASSES_FIELD.setAccessible(true);
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}
    	}

    	@SubscribeEvent
    	public static void onTooltip(ItemTooltipEvent event) {
     	   ItemStack stack = event.getItemStack();
     	   if (stack.is(CreateMixAndCleanModItems.HAZARD_PROTECTION_HELMET.get())) {
            	int dur = getFilterDurability(stack);
            	event.getToolTip().add(Component.literal(durabilityColor(dur) + "Filter: " + dur + "/" + MAX_FILTER_DURABILITY));
        	}
        	if (stack.is(CreateMixAndCleanModItems.GASMASK_FILTER.get())) {
            	int dur = stack.hasTag() && stack.getTag().contains(FILTER_NBT)
                	? stack.getTag().getInt(FILTER_NBT)
                	: MAX_FILTER_DURABILITY;
            	event.getToolTip().add(Component.literal(durabilityColor(dur) + "Durability: " + dur + "/" + MAX_FILTER_DURABILITY));
        	}
    	}

    	@SubscribeEvent
    	public static void onClientTick(TickEvent.ClientTickEvent event) {
        	if (event.phase != TickEvent.Phase.END) return;
        	Minecraft mc = Minecraft.getInstance();
        	if (mc.player == null || mc.level == null) return;

        	boolean currentlyWearing = isWearingProtectiveGear();

        	if (currentlyWearing != wasWearingProtection) {
        	    wasWearingProtection = currentlyWearing;
        	    goggleShaderActive = false;
        	    mc.levelRenderer.allChanged();
        	    if (!currentlyWearing) {
        	        mc.gameRenderer.shutdownEffect();
        	        mc.player.removeEffect(MobEffects.NIGHT_VISION);
        	    }
        	}

        	if (currentlyWearing) {
        	    if (!goggleShaderActive) {
        	        loadGoggleShader(mc);
        	    }
        	    updateShaderTime(mc);

        	    if (shouldApplyNightVision()) {
        	        if (!mc.player.hasEffect(MobEffects.NIGHT_VISION) || mc.player.getEffect(MobEffects.NIGHT_VISION).getDuration() <= 220) {
         	           mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
         	       }
         	   } else {
         	        mc.player.removeEffect(MobEffects.NIGHT_VISION);
        	    }
        	}
    	}

    	private static void loadGoggleShader(Minecraft mc) {
    	    try {
    	        mc.gameRenderer.loadEffect(GOGGLE_SHADER);
    	        goggleShaderActive = true;
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        goggleShaderActive = false;
    	    }
    	}

    	@SuppressWarnings("unchecked")
    	private static void updateShaderTime(Minecraft mc) {
    	    if (POST_EFFECT_FIELD == null || PASSES_FIELD == null) return;
    	    try {
    	        net.minecraft.client.renderer.PostChain chain =
     	           (net.minecraft.client.renderer.PostChain) POST_EFFECT_FIELD.get(mc.gameRenderer);
     	       if (chain == null) {
     	           goggleShaderActive = false;
     	           return;
     	       }
     	       java.util.List<net.minecraft.client.renderer.PostPass> passes =
     	           (java.util.List<net.minecraft.client.renderer.PostPass>) PASSES_FIELD.get(chain);
     	       float time = (float)(mc.level.getGameTime() % 100000L) / 20.0f;
     	       for (net.minecraft.client.renderer.PostPass pass : passes) {
     	           pass.getEffect().safeGetUniform("Time").set(time);
     	       }
    	    } catch (Exception ignored) {}
    	}

    	@SubscribeEvent
    	public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
    	    if (!event.getOverlay().id().getPath().equals("hotbar")) return;
    	    if (!isWearingProtectiveGear()) return;

   	    	GuiGraphics graphics = event.getGuiGraphics();
        	int width = event.getWindow().getGuiScaledWidth();
        	int height = event.getWindow().getGuiScaledHeight();

        	RenderSystem.disableDepthTest();
        	RenderSystem.depthMask(false);
        	RenderSystem.enableBlend();
        	RenderSystem.defaultBlendFunc();

        	RenderSystem.setShader(net.minecraft.client.renderer.GameRenderer::getPositionTexShader);
        	RenderSystem.setShaderTexture(0, VIGNETTE_TEXTURE);
        	RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.85f);

        	com.mojang.blaze3d.vertex.BufferBuilder buf = com.mojang.blaze3d.vertex.Tesselator.getInstance().getBuilder();
        	buf.begin(com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS, com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX);
        	buf.vertex(0, height, 0).uv(0, 1).endVertex();
        	buf.vertex(width, height, 0).uv(1, 1).endVertex();
        	buf.vertex(width, 0, 0).uv(1, 0).endVertex();
        	buf.vertex(0, 0, 0).uv(0, 0).endVertex();
        	com.mojang.blaze3d.vertex.BufferUploader.drawWithShader(buf.end());

        	RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        	RenderSystem.disableBlend();
        	RenderSystem.depthMask(true);
        	RenderSystem.enableDepthTest();
    	}
	}
}