/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.BlockItem;

import net.mcreator.createmixandclean.item.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

@EventBusSubscriber
public class CreateMixAndCleanModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(CreateMixAndCleanMod.MODID);
	public static final DeferredItem<Item> WASTE_ROCK;
	public static final DeferredItem<Item> PURIFIED_IRON_ORE;
	public static final DeferredItem<Item> PURIFIED_GOLD_ORE;
	public static final DeferredItem<Item> PURIFIED_COPPER_ORE;
	public static final DeferredItem<Item> PURIFIED_ZINC_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_IRON;
	public static final DeferredItem<Item> DIRTY_COMBINED_GOLD;
	public static final DeferredItem<Item> DIRTY_COMBINED_COPPER;
	public static final DeferredItem<Item> DIRTY_COMBINED_ZINC;
	public static final DeferredItem<Item> COMBINED_IRON;
	public static final DeferredItem<Item> COMBINED_GOLD;
	public static final DeferredItem<Item> COMBINED_COPPER;
	public static final DeferredItem<Item> COMBINED_ZINC;
	public static final DeferredItem<Item> CHLORINE_GAS_BUCKET;
	public static final DeferredItem<Item> HYDROGEN_GAS_BUCKET;
	public static final DeferredItem<Item> HYDROCHLORIC_ACID_BUCKET;
	public static final DeferredItem<Item> CAUSTIC_SODA;
	public static final DeferredItem<Item> PURIFIED_LEAD_ORE;
	public static final DeferredItem<Item> PURIFIED_OSMIUM_ORE;
	public static final DeferredItem<Item> PURIFIED_TIN_ORE;
	public static final DeferredItem<Item> PURIFIED_URANIUM_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_LEAD;
	public static final DeferredItem<Item> DIRTY_COMBINED_OSMIUM;
	public static final DeferredItem<Item> DIRTY_COMBINED_TIN;
	public static final DeferredItem<Item> DIRTY_COMBINED_URANIUM;
	public static final DeferredItem<Item> COMBINED_LEAD;
	public static final DeferredItem<Item> COMBINED_OSMIUM;
	public static final DeferredItem<Item> COMBINED_TIN;
	public static final DeferredItem<Item> COMBINED_URANIUM;
	public static final DeferredItem<Item> NETHERITE_CRYSTAL;
	public static final DeferredItem<Item> CRUSHED_RAW_COBALT;
	public static final DeferredItem<Item> PURIFIED_COBALT_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_COBALT;
	public static final DeferredItem<Item> COMBINED_COBALT;
	public static final DeferredItem<Item> PURIFIED_ALUMINUM_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_ALUMINUM;
	public static final DeferredItem<Item> COMBINED_ALUMINUM;
	public static final DeferredItem<Item> PURIFIED_SILVER_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_SILVER;
	public static final DeferredItem<Item> COMBINED_SILVER;
	public static final DeferredItem<Item> PURIFIED_NICKEL_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_NICKEL;
	public static final DeferredItem<Item> COMBINED_NICKEL;
	public static final DeferredItem<Item> PURIFIED_PLATINUM_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_PLATINUM;
	public static final DeferredItem<Item> COMBINED_PLATINUM;
	public static final DeferredItem<Item> PEACOCK_TAIL_CHESTPLATE;
	public static final DeferredItem<Item> MUSIC_DISC_MYCELIUM_MEN;
	public static final DeferredItem<Item> PURIFIED_DESH_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_DESH;
	public static final DeferredItem<Item> COMBINED_DESH;
	public static final DeferredItem<Item> PURIFIED_OSTRUM_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_OSTRUM;
	public static final DeferredItem<Item> COMBINED_OSTRUM;
	public static final DeferredItem<Item> PURIFIED_CALORITE_ORE;
	public static final DeferredItem<Item> DIRTY_COMBINED_CALORITE;
	public static final DeferredItem<Item> COMBINED_CALORITE;
	public static final DeferredItem<Item> ELECTROLYZER;
	public static final DeferredItem<Item> TABLE_SALT;
	public static final DeferredItem<Item> IRON_SLURRY_BUCKET;
	public static final DeferredItem<Item> OXYGEN_GAS_BUCKET;
	public static final DeferredItem<Item> BAUXITE;
	public static final DeferredItem<Item> ALUMINA;
	public static final DeferredItem<Item> ALUMINUM_INGOT;
	public static final DeferredItem<Item> ALUMINA_SAND_PAPER;
	public static final DeferredItem<Item> ALUMINUM_BLOCK;
	public static final DeferredItem<Item> ALUMINUM_DOOR;
	public static final DeferredItem<Item> ALUMINUM_TRAPDOOR;
	public static final DeferredItem<Item> LIQUID_AIR_BUCKET;
	public static final DeferredItem<Item> NITROGEN_GAS_BUCKET;
	public static final DeferredItem<Item> AMMONIA_BUCKET;
	public static final DeferredItem<Item> OGI_HELMET;
	public static final DeferredItem<Item> UNFINISHED_OGI_GOGGLES;
	public static final DeferredItem<Item> ALUMINUM_SHEET;
	public static final DeferredItem<Item> HAZARD_PROTECTION_HELMET;
	public static final DeferredItem<Item> GASMASK_FILTER;
	public static final DeferredItem<Item> ELECTRODE;
	static {
		WASTE_ROCK = REGISTRY.register("waste_rock", WasteRockItem::new);
		PURIFIED_IRON_ORE = REGISTRY.register("purified_iron_ore", PurifiedIronOreItem::new);
		PURIFIED_GOLD_ORE = REGISTRY.register("purified_gold_ore", PurifiedGoldOreItem::new);
		PURIFIED_COPPER_ORE = REGISTRY.register("purified_copper_ore", PurifiedCopperOreItem::new);
		PURIFIED_ZINC_ORE = REGISTRY.register("purified_zinc_ore", PurifiedZincOreItem::new);
		DIRTY_COMBINED_IRON = REGISTRY.register("dirty_combined_iron", DirtyCombinedIronItem::new);
		DIRTY_COMBINED_GOLD = REGISTRY.register("dirty_combined_gold", DirtyCombinedGoldItem::new);
		DIRTY_COMBINED_COPPER = REGISTRY.register("dirty_combined_copper", DirtyCombinedCopperItem::new);
		DIRTY_COMBINED_ZINC = REGISTRY.register("dirty_combined_zinc", DirtyCombinedZincItem::new);
		COMBINED_IRON = REGISTRY.register("combined_iron", CombinedIronItem::new);
		COMBINED_GOLD = REGISTRY.register("combined_gold", CombinedGoldItem::new);
		COMBINED_COPPER = REGISTRY.register("combined_copper", CombinedCopperItem::new);
		COMBINED_ZINC = REGISTRY.register("combined_zinc", CombinedZincItem::new);
		CHLORINE_GAS_BUCKET = REGISTRY.register("chlorine_gas_bucket", ChlorineGasItem::new);
		HYDROGEN_GAS_BUCKET = REGISTRY.register("hydrogen_gas_bucket", HydrogenGasItem::new);
		HYDROCHLORIC_ACID_BUCKET = REGISTRY.register("hydrochloric_acid_bucket", HydrochloricAcidItem::new);
		CAUSTIC_SODA = REGISTRY.register("caustic_soda", CausticSodaItem::new);
		PURIFIED_LEAD_ORE = REGISTRY.register("purified_lead_ore", PurifiedLeadOreItem::new);
		PURIFIED_OSMIUM_ORE = REGISTRY.register("purified_osmium_ore", PurifiedOsmiumOreItem::new);
		PURIFIED_TIN_ORE = REGISTRY.register("purified_tin_ore", PurifiedTinOreItem::new);
		PURIFIED_URANIUM_ORE = REGISTRY.register("purified_uranium_ore", PurifiedUraniumOreItem::new);
		DIRTY_COMBINED_LEAD = REGISTRY.register("dirty_combined_lead", DirtyCombinedLeadItem::new);
		DIRTY_COMBINED_OSMIUM = REGISTRY.register("dirty_combined_osmium", DirtyCombinedOsmiumItem::new);
		DIRTY_COMBINED_TIN = REGISTRY.register("dirty_combined_tin", DirtyCombinedTinItem::new);
		DIRTY_COMBINED_URANIUM = REGISTRY.register("dirty_combined_uranium", DirtyCombinedUraniumItem::new);
		COMBINED_LEAD = REGISTRY.register("combined_lead", CombinedLeadItem::new);
		COMBINED_OSMIUM = REGISTRY.register("combined_osmium", CombinedOsmiumItem::new);
		COMBINED_TIN = REGISTRY.register("combined_tin", CombinedTinItem::new);
		COMBINED_URANIUM = REGISTRY.register("combined_uranium", CombinedUraniumItem::new);
		NETHERITE_CRYSTAL = REGISTRY.register("netherite_crystal", NetheriteCrystalItem::new);
		CRUSHED_RAW_COBALT = REGISTRY.register("crushed_raw_cobalt", CrushedRawCobaltItem::new);
		PURIFIED_COBALT_ORE = REGISTRY.register("purified_cobalt_ore", PurifiedCobaltOreItem::new);
		DIRTY_COMBINED_COBALT = REGISTRY.register("dirty_combined_cobalt", DirtyCombinedCobaltItem::new);
		COMBINED_COBALT = REGISTRY.register("combined_cobalt", CombinedCobaltItem::new);
		PURIFIED_ALUMINUM_ORE = REGISTRY.register("purified_aluminum_ore", PurifiedAluminumOreItem::new);
		DIRTY_COMBINED_ALUMINUM = REGISTRY.register("dirty_combined_aluminum", DirtyCombinedAluminumItem::new);
		COMBINED_ALUMINUM = REGISTRY.register("combined_aluminum", CombinedAluminumItem::new);
		PURIFIED_SILVER_ORE = REGISTRY.register("purified_silver_ore", PurifiedSilverOreItem::new);
		DIRTY_COMBINED_SILVER = REGISTRY.register("dirty_combined_silver", DirtyCombinedSilverItem::new);
		COMBINED_SILVER = REGISTRY.register("combined_silver", CombinedSilverItem::new);
		PURIFIED_NICKEL_ORE = REGISTRY.register("purified_nickel_ore", PurifiedNickelOreItem::new);
		DIRTY_COMBINED_NICKEL = REGISTRY.register("dirty_combined_nickel", DirtyCombinedNickelItem::new);
		COMBINED_NICKEL = REGISTRY.register("combined_nickel", CombinedNickelItem::new);
		PURIFIED_PLATINUM_ORE = REGISTRY.register("purified_platinum_ore", PurifiedPlatinumOreItem::new);
		DIRTY_COMBINED_PLATINUM = REGISTRY.register("dirty_combined_platinum", DirtyCombinedPlatinumItem::new);
		COMBINED_PLATINUM = REGISTRY.register("combined_platinum", CombinedPlatinumItem::new);
		PEACOCK_TAIL_CHESTPLATE = REGISTRY.register("peacock_tail_chestplate", PeacockTailItem.Chestplate::new);
		MUSIC_DISC_MYCELIUM_MEN = REGISTRY.register("music_disc_mycelium_men", MusicDiscMyceliumMenItem::new);
		PURIFIED_DESH_ORE = REGISTRY.register("purified_desh_ore", PurifiedDeshOreItem::new);
		DIRTY_COMBINED_DESH = REGISTRY.register("dirty_combined_desh", DirtyCombinedDeshItem::new);
		COMBINED_DESH = REGISTRY.register("combined_desh", CombinedDeshItem::new);
		PURIFIED_OSTRUM_ORE = REGISTRY.register("purified_ostrum_ore", PurifiedOstrumOreItem::new);
		DIRTY_COMBINED_OSTRUM = REGISTRY.register("dirty_combined_ostrum", DirtyCombinedOstrumItem::new);
		COMBINED_OSTRUM = REGISTRY.register("combined_ostrum", CombinedOstrumItem::new);
		PURIFIED_CALORITE_ORE = REGISTRY.register("purified_calorite_ore", PurifiedCaloriteOreItem::new);
		DIRTY_COMBINED_CALORITE = REGISTRY.register("dirty_combined_calorite", DirtyCombinedCaloriteItem::new);
		COMBINED_CALORITE = REGISTRY.register("combined_calorite", CombinedCaloriteItem::new);
		ELECTROLYZER = block(CreateMixAndCleanModBlocks.ELECTROLYZER);
		TABLE_SALT = REGISTRY.register("table_salt", TableSaltItem::new);
		IRON_SLURRY_BUCKET = REGISTRY.register("iron_slurry_bucket", IronSlurryItem::new);
		OXYGEN_GAS_BUCKET = REGISTRY.register("oxygen_gas_bucket", OxygenGasItem::new);
		BAUXITE = block(CreateMixAndCleanModBlocks.BAUXITE);
		ALUMINA = REGISTRY.register("alumina", AluminaItem::new);
		ALUMINUM_INGOT = REGISTRY.register("aluminum_ingot", AluminumIngotItem::new);
		ALUMINA_SAND_PAPER = REGISTRY.register("alumina_sand_paper", AluminaSandPaperItem::new);
		ALUMINUM_BLOCK = block(CreateMixAndCleanModBlocks.ALUMINUM_BLOCK);
		ALUMINUM_DOOR = doubleBlock(CreateMixAndCleanModBlocks.ALUMINUM_DOOR);
		ALUMINUM_TRAPDOOR = block(CreateMixAndCleanModBlocks.ALUMINUM_TRAPDOOR);
		LIQUID_AIR_BUCKET = REGISTRY.register("liquid_air_bucket", LiquidAirItem::new);
		NITROGEN_GAS_BUCKET = REGISTRY.register("nitrogen_gas_bucket", NitrogenGasItem::new);
		AMMONIA_BUCKET = REGISTRY.register("ammonia_bucket", AmmoniaItem::new);
		OGI_HELMET = REGISTRY.register("ogi_helmet", OGIItem.Helmet::new);
		UNFINISHED_OGI_GOGGLES = REGISTRY.register("unfinished_ogi_goggles", UnfinishedOGIGogglesItem::new);
		ALUMINUM_SHEET = REGISTRY.register("aluminum_sheet", AluminumSheetItem::new);
		HAZARD_PROTECTION_HELMET = REGISTRY.register("hazard_protection_helmet", HazardProtectionItem.Helmet::new);
		GASMASK_FILTER = REGISTRY.register("gasmask_filter", GasmaskFilterItem::new);
		ELECTRODE = REGISTRY.register("electrode", ElectrodeItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), IRON_SLURRY_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), NITROGEN_GAS_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), CHLORINE_GAS_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), AMMONIA_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), LIQUID_AIR_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), HYDROGEN_GAS_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), HYDROCHLORIC_ACID_BUCKET.get());
		event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> new FluidBucketWrapper(stack), OXYGEN_GAS_BUCKET.get());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}

	private static DeferredItem<Item> doubleBlock(DeferredHolder<Block, Block> block) {
		return doubleBlock(block, new Item.Properties());
	}

	private static DeferredItem<Item> doubleBlock(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem(block.get(), properties));
	}
}