
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CreateMixAndCleanModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateMixAndCleanMod.MODID);
	public static final RegistryObject<CreativeModeTab> CREATE_MIX_AND_CLEAN = REGISTRY.register("create_mix_and_clean",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.create_mix_and_clean.create_mix_and_clean")).icon(() -> new ItemStack(CreateMixAndCleanModItems.WASTE_ROCK.get())).displayItems((parameters, tabData) -> {
				tabData.accept(CreateMixAndCleanModItems.WASTE_ROCK.get());
				tabData.accept(CreateMixAndCleanModItems.CAUSTIC_SODA.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_IRON_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.DIRTY_COMBINED_IRON.get());
				tabData.accept(CreateMixAndCleanModItems.COMBINED_IRON.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_GOLD_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.DIRTY_COMBINED_GOLD.get());
				tabData.accept(CreateMixAndCleanModItems.COMBINED_GOLD.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_COPPER_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.DIRTY_COMBINED_COPPER.get());
				tabData.accept(CreateMixAndCleanModItems.COMBINED_COPPER.get());
				tabData.accept(CreateMixAndCleanModItems.PURIFIED_ZINC_ORE.get());
				tabData.accept(CreateMixAndCleanModItems.DIRTY_COMBINED_ZINC.get());
				tabData.accept(CreateMixAndCleanModItems.COMBINED_ZINC.get());
				tabData.accept(CreateMixAndCleanModItems.CHLORINE_GAS_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.HYDROGEN_GAS_BUCKET.get());
				tabData.accept(CreateMixAndCleanModItems.HYDROCHLORIC_ACID_BUCKET.get());
			}).build());
}
