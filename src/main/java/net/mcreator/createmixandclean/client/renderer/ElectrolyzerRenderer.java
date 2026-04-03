package net.mcreator.createmixandclean.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.mcreator.createmixandclean.blockentity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

public class ElectrolyzerRenderer
        extends KineticBlockEntityRenderer<ElectrolyzerBlockEntity> {

    private static final float MAX_PLUNGE = 0.9375f;

    public ElectrolyzerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    protected void renderSafe(ElectrolyzerBlockEntity be, float partialTicks,
                               PoseStack ms, MultiBufferSource buffers,
                               int light, int overlay) {

        // Skip if Flywheel is handling visualization
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        BlockState state = be.getBlockState();
        Direction facing = state.getValue(
                com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING);

        // Render the rotating shaft body via the standard kinetic renderer
        super.renderSafe(be, partialTicks, ms, buffers, light, overlay);

        // Interpolate head plunge offset
        float headOffset = be.prevHeadOffset
                + (be.headOffset - be.prevHeadOffset) * partialTicks;
        float yTranslation = -headOffset * MAX_PLUNGE;

        SuperByteBuffer headRender = CachedBuffers.partialFacing(
                CreateMixAndCleanPartialModels.ELECTROLYZER_HEAD,
                state,
                Direction.UP); // UP = model baked upright, facing param rotates it

        ((SuperByteBuffer) headRender.translate(0, yTranslation, 0))
                .light(light)
                .renderInto(ms, buffers.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(ElectrolyzerBlockEntity be) {
        // Tell the kinetic base renderer to draw the shaft spinning on the correct axis
        return shaft(be.getBlockState()
                .getValue(com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING)
                .getAxis());
    }
}