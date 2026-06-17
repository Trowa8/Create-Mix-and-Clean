package net.mcreator.createmixandclean.compat.jei;

import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlocks;
import net.mcreator.createmixandclean.init.CreateMixAndCleanPartialModels;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

public class AnimatedElectrolyzer extends AnimatedKinetics {

    public static final AnimatedElectrolyzer INSTANCE = new AnimatedElectrolyzer();

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        graphics.pose().pushPose();

        graphics.pose().translate(xOffset, yOffset, 200f);
        graphics.pose().mulPose(com.mojang.math.Axis.XP.rotationDegrees(-15.5f));
        graphics.pose().mulPose(com.mojang.math.Axis.YP.rotationDegrees(22.5f));

        blockElement(shaft(Direction.Axis.Z))
                .atLocal(0, 0, 0)
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(24)
                .render(graphics);

        blockElement(CreateMixAndCleanModBlocks.ELECTROLYZER.get().defaultBlockState())
                .atLocal(0, 0, 0)
                .scale(24)
                .render(graphics);

        blockElement(com.simibubi.create.AllBlocks.BASIN.get().defaultBlockState())
                .atLocal(0, 1.5, 0)
                .scale(24)
                .render(graphics);

        long cycle = System.currentTimeMillis() % 5000;
        float amplitude = 0.75f;
        float offset = 0.5f;
        float headY;

        if (cycle < 1000) {
            float t = cycle / 1000.0f;
            headY = offset + amplitude * (1 - (float) Math.cos(t * Math.PI)) / 2;
        } else if (cycle < 4000) {
            headY = offset + amplitude;
        } else {
            float t = (cycle - 4000) / 1000.0f;
            headY = offset + amplitude * (1 + (float) Math.cos(t * Math.PI)) / 2;
        }

        blockElement(CreateMixAndCleanPartialModels.ELECTROLYZER_HEAD)
                .atLocal(0, headY, 0)
                .scale(24)
                .render(graphics);

        graphics.pose().popPose();
    }
}