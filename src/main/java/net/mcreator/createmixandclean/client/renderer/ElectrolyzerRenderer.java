package net.mcreator.createmixandclean.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.mcreator.createmixandclean.block.entity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

public class ElectrolyzerRenderer
        extends KineticBlockEntityRenderer<ElectrolyzerBlockEntity> {

    // How far the electrode plunges into the basin (in blocks).
    // 1.0 = full block down. Adjust visually if needed.
    private static final float MAX_PLUNGE = 1.0f;

    public ElectrolyzerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    protected void renderSafe(ElectrolyzerBlockEntity be, float partialTicks,
                               PoseStack ms, MultiBufferSource buffers,
                               int light, int overlay) {

        BlockState state = be.getBlockState();

        // ── Shaft ────────────────────────────────────────────────────────────
        // Bypass super.renderSafe() — KineticBlockEntityRenderer.renderSafe()
        // also exits early when VisualizationManager.supportsVisualization() is
        // true, which it always is in normal play. Since we have no Flywheel
        // Visual registered, calling the static method directly is the only path
        // that produces pixels for the shaft.
        KineticBlockEntityRenderer.renderRotatingKineticBlock(
                be,
                shaft(state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING).getAxis()),
                ms,
                buffers.getBuffer(RenderType.solid()),
                light);

        // ── Electrode head ────────────────────────────────────────────────────
        // Use CachedBuffers.partial (NOT partialFacing) — the electrode goes
        // straight down regardless of which direction the block faces.
        // partialFacing rotates the model toward a horizontal facing direction,
        // which is why it was producing a horizontal result.
        float offset = be.prevHeadOffset
                + (be.headOffset - be.prevHeadOffset) * partialTicks;

        ((SuperByteBuffer) CachedBuffers.partial(
                CreateMixAndCleanPartialModels.ELECTROLYZER_HEAD,
                state)
                .translate(0f, -(offset * MAX_PLUNGE) - 0.5f, 0f))
                .light(light)
                .renderInto(ms, buffers.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(ElectrolyzerBlockEntity be) {
        return shaft(be.getBlockState()
                .getValue(HorizontalKineticBlock.HORIZONTAL_FACING)
                .getAxis());
    }

    @Override
    public boolean shouldRenderOffScreen(ElectrolyzerBlockEntity be) {
        // Electrode extends below the 1x1x1 bounding box while plunging.
        // Without this, frustum culling clips it when viewed from certain angles.
        return true;
    }
}