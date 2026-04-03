package net.mcreator.createmixandclean.init;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class CreateMixAndCleanPartialModels {

    public static final PartialModel ELECTROLYZER_HEAD =
            new PartialModel(new ResourceLocation(
                    "create_mix_and_clean", "block/electrolyzer_head"));

    // Called during mod construction to force class loading and model registration
    public static void init() {}
}