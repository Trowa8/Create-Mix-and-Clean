package net.mcreator.createmixandclean.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlocks;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class CreateMixAndCleanJEI implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CreateMixAndCleanMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ElectrolyzerCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();
        var recipes = recipeManager.getAllRecipesFor(
                CreateMixAndCleanModRecipeTypes.ELECTROLYZING.get());
        registration.addRecipes(ElectrolyzerCategory.RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(CreateMixAndCleanModBlocks.ELECTROLYZER.get().asItem()),
                ElectrolyzerCategory.RECIPE_TYPE);
    }
}