
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.mcreator.createmixandclean.item.WasteRockItem;
import net.mcreator.createmixandclean.item.PurifiedZincOreItem;
import net.mcreator.createmixandclean.item.PurifiedIronOreItem;
import net.mcreator.createmixandclean.item.PurifiedGoldOreItem;
import net.mcreator.createmixandclean.item.PurifiedCopperOreItem;
import net.mcreator.createmixandclean.item.HydrogenGasItem;
import net.mcreator.createmixandclean.item.HydrochloricAcidItem;
import net.mcreator.createmixandclean.item.DirtyCombinedZincItem;
import net.mcreator.createmixandclean.item.DirtyCombinedIronItem;
import net.mcreator.createmixandclean.item.DirtyCombinedGoldItem;
import net.mcreator.createmixandclean.item.DirtyCombinedCopperItem;
import net.mcreator.createmixandclean.item.CombinedZincItem;
import net.mcreator.createmixandclean.item.CombinedIronItem;
import net.mcreator.createmixandclean.item.CombinedGoldItem;
import net.mcreator.createmixandclean.item.CombinedCopperItem;
import net.mcreator.createmixandclean.item.ChlorineGasItem;
import net.mcreator.createmixandclean.item.CausticSodaItem;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<Item> WASTE_ROCK = REGISTRY.register("waste_rock", () -> new WasteRockItem());
	public static final RegistryObject<Item> PURIFIED_IRON_ORE = REGISTRY.register("purified_iron_ore", () -> new PurifiedIronOreItem());
	public static final RegistryObject<Item> PURIFIED_GOLD_ORE = REGISTRY.register("purified_gold_ore", () -> new PurifiedGoldOreItem());
	public static final RegistryObject<Item> PURIFIED_COPPER_ORE = REGISTRY.register("purified_copper_ore", () -> new PurifiedCopperOreItem());
	public static final RegistryObject<Item> PURIFIED_ZINC_ORE = REGISTRY.register("purified_zinc_ore", () -> new PurifiedZincOreItem());
	public static final RegistryObject<Item> DIRTY_COMBINED_IRON = REGISTRY.register("dirty_combined_iron", () -> new DirtyCombinedIronItem());
	public static final RegistryObject<Item> DIRTY_COMBINED_GOLD = REGISTRY.register("dirty_combined_gold", () -> new DirtyCombinedGoldItem());
	public static final RegistryObject<Item> DIRTY_COMBINED_COPPER = REGISTRY.register("dirty_combined_copper", () -> new DirtyCombinedCopperItem());
	public static final RegistryObject<Item> DIRTY_COMBINED_ZINC = REGISTRY.register("dirty_combined_zinc", () -> new DirtyCombinedZincItem());
	public static final RegistryObject<Item> COMBINED_IRON = REGISTRY.register("combined_iron", () -> new CombinedIronItem());
	public static final RegistryObject<Item> COMBINED_GOLD = REGISTRY.register("combined_gold", () -> new CombinedGoldItem());
	public static final RegistryObject<Item> COMBINED_COPPER = REGISTRY.register("combined_copper", () -> new CombinedCopperItem());
	public static final RegistryObject<Item> COMBINED_ZINC = REGISTRY.register("combined_zinc", () -> new CombinedZincItem());
	public static final RegistryObject<Item> CHLORINE_GAS_BUCKET = REGISTRY.register("chlorine_gas_bucket", () -> new ChlorineGasItem());
	public static final RegistryObject<Item> HYDROGEN_GAS_BUCKET = REGISTRY.register("hydrogen_gas_bucket", () -> new HydrogenGasItem());
	public static final RegistryObject<Item> HYDROCHLORIC_ACID_BUCKET = REGISTRY.register("hydrochloric_acid_bucket", () -> new HydrochloricAcidItem());
	public static final RegistryObject<Item> CAUSTIC_SODA = REGISTRY.register("caustic_soda", () -> new CausticSodaItem());
	// Start of user code block custom items
	// End of user code block custom items
}
