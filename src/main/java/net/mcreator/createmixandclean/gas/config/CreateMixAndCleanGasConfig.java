package net.mcreator.createmixandclean.gas.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CreateMixAndCleanGasConfig {

    public enum CellModel { SINGLE, MIXTURE }

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue MASTER_ENABLED =
            BUILDER.comment("Enable atmospheric gas simulation entirely").define("masterEnabled", true);

    public static final ModConfigSpec.EnumValue<CellModel> CELL_MODEL =
            BUILDER.comment("Gas cell data model, switchable mid-save, triggers migration on chunk load")
                    .defineEnum("cellModel", CellModel.SINGLE);

    public static final ModConfigSpec.BooleanValue WIND_AWARENESS =
            BUILDER.define("windAwareness", true);

    public static final ModConfigSpec.BooleanValue OUTDOOR_DISSIPATION =
            BUILDER.define("outdoorDissipation", true);

    public static final ModConfigSpec.DoubleValue OUTDOOR_DECAY_RATE =
            BUILDER.defineInRange("outdoorDecayRate", 0.4, 0.0, 15.0);

    public static final ModConfigSpec.BooleanValue SETTLING_ENABLED =
            BUILDER.define("settlingEnabled", true);

    public static final ModConfigSpec.DoubleValue SETTLE_EPSILON =
            BUILDER.defineInRange("settleEpsilon", 0.02, 0.0, 1.0);

    public static final ModConfigSpec.IntValue SETTLE_STABLE_CYCLES =
            BUILDER.defineInRange("settleStableCycles", 3, 1, 20);

    public static final ModConfigSpec.IntValue POCKET_RECOMPUTE_INTERVAL =
            BUILDER.defineInRange("pocketRecomputeIntervalTicks", 60, 20, 2000);

    public static final ModConfigSpec.IntValue MAX_POCKET_CELLS =
            BUILDER.defineInRange("maxPocketCells", 500, 50, 5000);

    public static final ModConfigSpec.IntValue MAX_CELL_UPDATES_PER_TICK =
            BUILDER.defineInRange("maxCellUpdatesPerTick", 64, 1, 2000);

    public static final ModConfigSpec.BooleanValue VOL_EXTRACTION_ENABLED =
            BUILDER.comment("Reserved for the future collector/extraction block").define("volExtractionEnabled", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
