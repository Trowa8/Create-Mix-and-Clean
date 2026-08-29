package net.mcreator.createmixandclean.gas.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.gas.GasType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class GasTextureColorSampler {

    private static final Map<ResourceLocation, NativeImage> CACHE = new HashMap<>();

    private GasTextureColorSampler() {}

    public static float[] sampleColor(GasType gasType, RandomSource random) {
        ResourceLocation stillTexture = IClientFluidTypeExtensions.of(gasType.getFluidType()).getStillTexture();
        CreateMixAndCleanMod.LOGGER.info("Sampling gas texture {} for {}", stillTexture, gasType);
        NativeImage image = getOrLoad(stillTexture);
        if (image == null) {
            CreateMixAndCleanMod.LOGGER.info("Gas texture load failed for {} using fallback", gasType);
            return gasType.getFallbackColor();
        }

        for (int attempt = 0; attempt < 8; attempt++) {
            int x = random.nextInt(image.getWidth());
            int y = random.nextInt(image.getHeight());
            int pixel = image.getPixelRGBA(x, y);
            int alpha = FastColor.ABGR32.alpha(pixel);
            if (alpha < 32) continue;

            float r = FastColor.ABGR32.red(pixel) / 255f;
            float g = FastColor.ABGR32.green(pixel) / 255f;
            float b = FastColor.ABGR32.blue(pixel) / 255f;
            CreateMixAndCleanMod.LOGGER.info("Gas color sampled for {} = ({}, {}, {})", gasType, r, g, b);
            return new float[]{r, g, b};
        }
        CreateMixAndCleanMod.LOGGER.info("Gas texture had no alpha-visible pixels for {} using fallback", gasType);
        return gasType.getFallbackColor();
    }

    private static NativeImage getOrLoad(ResourceLocation stillTexture) {
        return CACHE.computeIfAbsent(stillTexture, loc -> {
            ResourceLocation full = ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "textures/" + loc.getPath() + ".png");
            try {
                Resource resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(full);
                try (InputStream stream = resource.open()) {
                    return NativeImage.read(stream);
            }
            } catch (Exception e) {
                return null;
            }
        });
    }
}