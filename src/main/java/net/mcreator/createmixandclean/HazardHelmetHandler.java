package net.mcreator.createmixandclean;

import net.mcreator.createmixandclean.fluid.types.AmmoniaFluidType;
import net.mcreator.createmixandclean.fluid.types.HydrogenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.OxygenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.NitrogenGasFluidType;
import net.mcreator.createmixandclean.fluid.types.ChlorineGasFluidType;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModItems;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.fluids.FluidStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class HazardHelmetHandler {

    private static final int MAX_FILTER_DURABILITY = 120;
    private static final String FILTER_NBT = "FilterDurability";
    private static final Set<UUID> wasInGas = new HashSet<>();
    private static final ResourceLocation VIGNETTE_TEXTURE = ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "textures/screens/goggle_vignette.png");
    private static final ResourceLocation GOGGLE_SHADER = ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "shaders/post/goggle_effect.json");
    private static boolean goggleShaderActive = false;

    @SubscribeEvent
    public static void onLivingTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.is(CreateMixAndCleanModItems.HAZARD_PROTECTION_HELMET.get())) return;
        helmet.set(DataComponents.UNBREAKABLE, new Unbreakable(false));

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
        CustomData heldData = held.get(DataComponents.CUSTOM_DATA);
        int newDurability = (heldData != null && heldData.contains(FILTER_NBT))
            ? heldData.copyTag().getInt(FILTER_NBT)
            : MAX_FILTER_DURABILITY;

        ItemStack oldFilter = new ItemStack(CreateMixAndCleanModItems.GASMASK_FILTER.get());
        CompoundTag oldTag = new CompoundTag();
        oldTag.putInt(FILTER_NBT, oldDurability);
        oldFilter.set(DataComponents.CUSTOM_DATA, CustomData.of(oldTag));
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
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(helmet.getItem());
        if (key == null) return false;
        String path = key.getPath();
        return path.equals("ogi_helmet") || path.equals("hazard_protection_helmet");
    }

    private static boolean shouldApplyNightVision() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return false;
        ItemStack helmet = mc.player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return false;
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(helmet.getItem());
        if (key == null) return false;
        return key.getPath().equals("hazard_protection_helmet");
    }

    private static boolean isFilteredGas(FluidState state) {
        return state.getFluidType() instanceof AmmoniaFluidType
            || state.getFluidType() instanceof HydrogenGasFluidType
            || state.getFluidType() instanceof OxygenGasFluidType
            || state.getFluidType() instanceof NitrogenGasFluidType
            || state.getFluidType() instanceof ChlorineGasFluidType;
    }

    private static int getFilterDurability(ItemStack helmet) {
        CustomData data = helmet.get(DataComponents.CUSTOM_DATA);
        if (data == null || !data.contains(FILTER_NBT)) return MAX_FILTER_DURABILITY;
        return data.copyTag().getInt(FILTER_NBT);
    }

    private static void setFilterDurability(ItemStack helmet, int value) {
        helmet.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, existing -> {
            CompoundTag tag = existing.copyTag();
            tag.putInt(FILTER_NBT, Math.max(0, value));
            return CustomData.of(tag);
        });
    }

    private static String durabilityColor(int dur) {
        return dur > 40 ? "§a" : dur > 20 ? "§e" : "§c";
    }

    public static IClientFluidTypeExtensions createGasFluidExtensions(ResourceLocation defaultStill, ResourceLocation defaultFlow) {
        return new IClientFluidTypeExtensions() {
            private static final ResourceLocation OGI_STILL = ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "block/ogi_gas_still");
            private static final ResourceLocation OGI_FLOW = ResourceLocation.fromNamespaceAndPath("create_mix_and_clean", "block/ogi_gas_flow");

            @Override
            public ResourceLocation getStillTexture() { return defaultStill; }

            @Override
            public ResourceLocation getFlowingTexture() { return defaultFlow; }

            @Override
            public ResourceLocation getStillTexture(FluidState state, BlockAndTintGetter world, BlockPos pos) {
                return isWearingProtectiveGear() ? OGI_STILL : defaultStill;
            }

            @Override
            public ResourceLocation getFlowingTexture(FluidState state, BlockAndTintGetter world, BlockPos pos) {
                return isWearingProtectiveGear() ? OGI_FLOW : defaultFlow;
            }

            @Override
            public int getTintColor() {
                return isWearingProtectiveGear() ? (255 << 24) | (230 << 16) | (230 << 8) | 230 : -1;
            }

            @Override
            public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
                return isWearingProtectiveGear() ? (255 << 24) | (230 << 16) | (230 << 8) | 230 : -1;
            }

            @Override
            public int getTintColor(FluidStack stack) {
                return isWearingProtectiveGear() ? (255 << 24) | (230 << 16) | (230 << 8) | 230 : -1;
            }
        };
    }

    @EventBusSubscriber(modid = "create_mix_and_clean", value = Dist.CLIENT)
    public static class ClientForgeEvents {

        private static boolean wasWearingProtection = false;
        private static Field POST_EFFECT_FIELD;
        private static Field PASSES_FIELD;

        static {
            try {
                POST_EFFECT_FIELD = GameRenderer.class.getDeclaredField("postEffect");
                POST_EFFECT_FIELD.setAccessible(true);
                PASSES_FIELD = PostChain.class.getDeclaredField("passes");
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
                CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                int dur = (data != null && data.contains(FILTER_NBT))
                    ? data.copyTag().getInt(FILTER_NBT)
                    : MAX_FILTER_DURABILITY;
                event.getToolTip().add(Component.literal(durabilityColor(dur) + "Durability: " + dur + "/" + MAX_FILTER_DURABILITY));
            }
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
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
                PostChain chain = (PostChain) POST_EFFECT_FIELD.get(mc.gameRenderer);
                if (chain == null) {
                    goggleShaderActive = false;
                    return;
                }
                List<PostPass> passes = (List<PostPass>) PASSES_FIELD.get(chain);
                float time = (float) (mc.level.getGameTime() % 100000L) / 20.0f;
                for (PostPass pass : passes) {
                    pass.getEffect().safeGetUniform("Time").set(time);
                }
            } catch (Exception ignored) {}
        }

        @SubscribeEvent
        public static void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
            if (!event.getName().equals(VanillaGuiLayers.HOTBAR)) return;
            if (!isWearingProtectiveGear()) return;

            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, VIGNETTE_TEXTURE);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.85f);

            BufferBuilder buf = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            buf.addVertex(0, height, 0).setUv(0, 1);
            buf.addVertex(width, height, 0).setUv(1, 1);
            buf.addVertex(width, 0, 0).setUv(1, 0);
            buf.addVertex(0, 0, 0).setUv(0, 0);
            BufferUploader.drawWithShader(buf.buildOrThrow());

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }
}