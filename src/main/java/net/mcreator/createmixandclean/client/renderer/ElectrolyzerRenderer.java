package main.java.net.mcreator.createmixandclean.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.mcreator.createmixandclean.blockentity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ElectrolyzerRenderer
        extends SafeBlockEntityRenderer<ElectrolyzerBlockEntity> {

    /** Max downward travel in blocks (adjust to fit your model) */
    private static final float MAX_PLUNGE = 0.775f; // 15/16 of a block

    private static final ResourceLocation HEAD_MODEL_LOC =
            new ResourceLocation("create_mix_and_clean", "block/electrolyzer_head");
    // In a client-only init class
    public class CreateMixAndCleanPartialModels {
        public static final PartialModel ELECTROLYZER_HEAD =
            new PartialModel(new ResourceLocation("create_mix_and_clean", "block/electrolyzer_head"));
    }

    public ElectrolyzerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    protected void renderSafe(ElectrolyzerBlockEntity be,
                               float partialTicks,
                               PoseStack ms,
                               MultiBufferSource buffers,
                               int light, int overlay) {

        // Interpolate head position for smooth animation
        float offset = be.prevHeadOffset
                + (be.headOffset - be.prevHeadOffset) * partialTicks;
        float yTranslation = -offset * MAX_PLUNGE;

        BlockState state = be.getBlockState();

        // Render the animated head
        SuperByteBuffer head = CachedBufferer.partial(
                // Use the partial model registered for the head
                // Adjust this call to match how you register the model
                CreateMixAndCleanModBlocks.ELECTROLYZER.get().defaultBlockState(),
                state
        );

        // Apply vertical translation for the plunge motion
        ms.pushPose();
        ms.translate(0, yTranslation, 0);
        head.light(light)
            .renderInto(ms, buffers.getBuffer(RenderType.solid()));
        ms.popPose();
    }
}