package net.mcreator.createmixandclean.client.renderer;  // ← was main.java.net.mcreator...

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.createmixandclean.blockentity.ElectrolyzerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

/**
 * Stub renderer — head animation to be implemented once the block compiles.
 * Will be wired up in a client-side event subscriber alongside PartialModel registration.
 */
public class ElectrolyzerRenderer implements BlockEntityRenderer<ElectrolyzerBlockEntity> {

    public ElectrolyzerRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(ElectrolyzerBlockEntity be, float partialTick,
                       PoseStack ms, MultiBufferSource buffers,
                       int light, int overlay) {
        // Animation rendering will go here once PartialModel + CachedBufferer
        // imports are resolved against the exact Create version on the classpath.
    }
}