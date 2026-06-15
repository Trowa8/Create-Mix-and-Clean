package net.mcreator.createmixandclean.compat.jei;

import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.createmod.catnip.gui.element.GuiGameElement;
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
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(24)
                .render(graphics);

        blockElement(CreateMixAndCleanModBlocks.ELECTROLYZER.get().defaultBlockState())
                .scale(24)
                .render(graphics);

        blockElement(com.simibubi.create.AllBlocks.BASIN.get().defaultBlockState())
                .atLocal(0, -1, 0)
                .scale(24)
                .render(graphics);

        float headY = (float) Math.sin(
                (System.currentTimeMillis() % 2000) / 2300.0 * Math.PI * 2) * 0.03f;

        blockElement(CreateMixAndCleanPartialModels.ELECTROLYZER_HEAD)
                .atLocal(0, headY, 0)
                .scale(24)
                .render(graphics);

        graphics.pose().popPose();
    }
}