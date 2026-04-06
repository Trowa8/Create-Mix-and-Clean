package net.mcreator.createmixandclean.init;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class CreateMixAndCleanPartialModels {

    public static final PartialModel ELECTROLYZER_HEAD =
            PartialModel.of(new ResourceLocation(
                    "create_mix_and_clean", "block/electrolyzer_head"));

    public static void init() {}
}