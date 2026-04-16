package net.mcreator.createmixandclean.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.mcreator.createmixandclean.block.entity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanPartialModels;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

public class ElectrolyzerRenderer
        extends KineticBlockEntityRenderer<ElectrolyzerBlockEntity> {

    private static final float MAX_PLUNGE = 1.0f;

    public ElectrolyzerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    protected void renderSafe(ElectrolyzerBlockEntity be, float partialTicks,
                               PoseStack ms, MultiBufferSource buffers,
                               int light, int overlay) {

        BlockState state = be.getBlockState();

        int fullBright = LightTexture.FULL_BRIGHT;

        KineticBlockEntityRenderer.renderRotatingKineticBlock(
                be,
                shaft(state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING).getAxis()),
                ms,
                buffers.getBuffer(RenderType.solid()),
                fullBright);

        float offset = be.prevHeadOffset
                + (be.headOffset - be.prevHeadOffset) * partialTicks;

        ((SuperByteBuffer) CachedBuffers.partial(
                CreateMixAndCleanPartialModels.ELECTROLYZER_HEAD,
                state)
                .translate(0f, -(offset * MAX_PLUNGE) - 0.56f, 0f))
                .light(fullBright)
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
        return true;
    }
}