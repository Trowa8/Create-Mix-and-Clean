package net.mcreator.createmixandclean.compat.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlocks;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class ElectrolyzerCategory implements mezz.jei.api.recipe.category.IRecipeCategory<ElectrolyzerRecipe> {

    public static final RecipeType<ElectrolyzerRecipe> RECIPE_TYPE = RecipeType.create(
            CreateMixAndCleanMod.MODID, "electrolyzing", ElectrolyzerRecipe.class);

    private static final int WIDTH  = 177;
    private static final int HEIGHT = 70;

    private final IDrawable background;
    private final IDrawable icon;

    public ElectrolyzerCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(WIDTH, HEIGHT);
        icon = guiHelper.createDrawableItemStack(
            new ItemStack(CreateMixAndCleanModBlocks.ELECTROLYZER.get()));
    }

    @Override
    public RecipeType<ElectrolyzerRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.create_mix_and_clean.electrolysis");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,
                          ElectrolyzerRecipe recipe,
                          IFocusGroup focuses) {

        List<Ingredient> ingredients = recipe.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 27, 16 + i * 19)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addIngredients(ingredients.get(i));
        }

        List<ItemStack> results = recipe.getResults();
        for (int i = 0; i < results.size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 131, 16 + i * 19)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addItemStack(results.get(i));
        }
    }

    @Override
    public void draw(ElectrolyzerRecipe recipe,
                     IRecipeSlotsView recipeSlotsView,
                     GuiGraphics graphics,
                     double mouseX, double mouseY) {

        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 30);
        AllGuiTextures.JEI_LONG_ARROW.render(graphics, 52, 35);

        AnimatedElectrolyzer.INSTANCE.draw(graphics, getBackground().getWidth() / 2, 22);
    }
}