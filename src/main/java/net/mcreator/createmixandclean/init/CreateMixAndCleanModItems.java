/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.mcreator.createmixandclean.item.*;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<Item> WASTE_ROCK;
	public static final RegistryObject<Item> PURIFIED_IRON_ORE;
	public static final RegistryObject<Item> PURIFIED_GOLD_ORE;
	public static final RegistryObject<Item> PURIFIED_COPPER_ORE;
	public static final RegistryObject<Item> PURIFIED_ZINC_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_IRON;
	public static final RegistryObject<Item> DIRTY_COMBINED_GOLD;
	public static final RegistryObject<Item> DIRTY_COMBINED_COPPER;
	public static final RegistryObject<Item> DIRTY_COMBINED_ZINC;
	public static final RegistryObject<Item> COMBINED_IRON;
	public static final RegistryObject<Item> COMBINED_GOLD;
	public static final RegistryObject<Item> COMBINED_COPPER;
	public static final RegistryObject<Item> COMBINED_ZINC;
	public static final RegistryObject<Item> CHLORINE_GAS_BUCKET;
	public static final RegistryObject<Item> HYDROGEN_GAS_BUCKET;
	public static final RegistryObject<Item> HYDROCHLORIC_ACID_BUCKET;
	public static final RegistryObject<Item> CAUSTIC_SODA;
	public static final RegistryObject<Item> PURIFIED_LEAD_ORE;
	public static final RegistryObject<Item> PURIFIED_OSMIUM_ORE;
	public static final RegistryObject<Item> PURIFIED_TIN_ORE;
	public static final RegistryObject<Item> PURIFIED_URANIUM_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_LEAD;
	public static final RegistryObject<Item> DIRTY_COMBINED_OSMIUM;
	public static final RegistryObject<Item> DIRTY_COMBINED_TIN;
	public static final RegistryObject<Item> DIRTY_COMBINED_URANIUM;
	public static final RegistryObject<Item> COMBINED_LEAD;
	public static final RegistryObject<Item> COMBINED_OSMIUM;
	public static final RegistryObject<Item> COMBINED_TIN;
	public static final RegistryObject<Item> COMBINED_URANIUM;
	public static final RegistryObject<Item> NETHERITE_CRYSTAL;
	public static final RegistryObject<Item> CRUSHED_RAW_COBALT;
	public static final RegistryObject<Item> PURIFIED_COBALT_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_COBALT;
	public static final RegistryObject<Item> COMBINED_COBALT;
	public static final RegistryObject<Item> PURIFIED_ALUMINUM_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_ALUMINUM;
	public static final RegistryObject<Item> COMBINED_ALUMINUM;
	public static final RegistryObject<Item> PURIFIED_SILVER_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_SILVER;
	public static final RegistryObject<Item> COMBINED_SILVER;
	public static final RegistryObject<Item> PURIFIED_NICKEL_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_NICKEL;
	public static final RegistryObject<Item> COMBINED_NICKEL;
	public static final RegistryObject<Item> PURIFIED_PLATINUM_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_PLATINUM;
	public static final RegistryObject<Item> COMBINED_PLATINUM;
	public static final RegistryObject<Item> PEACOCK_TAIL_CHESTPLATE;
	public static final RegistryObject<Item> MUSIC_DISC_MYCELIUM_MEN;
	public static final RegistryObject<Item> PURIFIED_DESH_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_DESH;
	public static final RegistryObject<Item> COMBINED_DESH;
	public static final RegistryObject<Item> PURIFIED_OSTRUM_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_OSTRUM;
	public static final RegistryObject<Item> COMBINED_OSTRUM;
	public static final RegistryObject<Item> PURIFIED_CALORITE_ORE;
	public static final RegistryObject<Item> DIRTY_COMBINED_CALORITE;
	public static final RegistryObject<Item> COMBINED_CALORITE;
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
	}
	// Start of user code block custom items
	// End of user code block custom items
}