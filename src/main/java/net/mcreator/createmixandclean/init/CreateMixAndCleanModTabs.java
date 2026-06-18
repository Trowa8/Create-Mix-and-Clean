/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateMixAndCleanMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_MIX_AND_CLEAN = REGISTRY.register("create_mix_and_clean",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.create_mix_and_clean.create_mix_and_clean")).icon(() -> new ItemStack(CreateMixAndCleanModItems.WASTE_ROCK.get())).displayItems((parameters, tabData) -> {
				tabData.accept(CreateMixAndCleanModItems.WASTE_ROCK.get());
				tabData.accept(CreateMixAndCleanModItems.CAUSTIC_SODA.get());
				tabData.accept(CreateMixAndCleanModItems.NETHERITE_CRYSTAL.get());
				tabData.accept(CreateMixAndCleanModItems.CRUSHED_RAW_COBALT.get());
				tabData.accept(CreateMixAndCleanModItems.CHLORINE_GAS_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.HYDROCHLORIC_ACID_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.LIQUID_AIR_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.IRON_SLURRY_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.HYDROGEN_GAS_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.OXYGEN_GAS_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.NITROGEN_GAS_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.AMMONIA_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_IRON_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_GOLD_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_COPPER_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_ZINC_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_OSMIUM_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_PLATINUM_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_SILVER_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_TIN_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_LEAD_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_ALUMINUM_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_URANIUM_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_NICKEL_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_COBALT_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_DESH_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_OSTRUM_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_CALORITE_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.MUSIC_DISC_MYCELIUM_MEN.get());
				tabData.accept(CreateMixAndCleanModBlocks.ELECTROLYZER.get().asItem());
				tabData.accept(CreateMixAndCleanModItems.ELECTRODE.get());
				tabData.accept(CreateMixAndCleanModItems.TABLE_SALT.get());
				tabData.accept(CreateMixAndCleanModBlocks.BAUXITE.get().asItem());
				tabData.accept(CreateMixAndCleanModItems.ALUMINA.get());
				tabData.accept(CreateMixAndCleanModItems.ALUMINUM_INGOT.get());
				tabData.accept(CreateMixAndCleanModItems.ALUMINUM_SHEET.get());
				tabData.accept(CreateMixAndCleanModItems.ALUMINA_SAND_PAPER.get());
				tabData.accept(CreateMixAndCleanModBlocks.ALUMINUM_BLOCK.get().asItem());
				tabData.accept(CreateMixAndCleanModBlocks.ALUMINUM_DOOR.get().asItem());
				tabData.accept(CreateMixAndCleanModBlocks.ALUMINUM_TRAPDOOR.get().asItem());
				tabData.accept(CreateMixAndCleanModItems.OGI_HELMET.get());
				tabData.accept(CreateMixAndCleanModItems.HAZARD_PROTECTION_HELMET.get());
				tabData.accept(CreateMixAndCleanModItems.GASMASK_FILTER.get());
			}).build());
}