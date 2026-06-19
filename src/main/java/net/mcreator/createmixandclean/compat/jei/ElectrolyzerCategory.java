package net.mcreator.createmixandclean.compat.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import mezz.jei.api.neoforge.NeoForgeTypes;
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
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class ElectrolyzerCategory implements mezz.jei.api.recipe.category.IRecipeCategory<ElectrolyzerRecipe> {

    public static final RecipeType<ElectrolyzerRecipe> RECIPE_TYPE = RecipeType.create(
            CreateMixAndCleanMod.MODID, "electrolyzing", ElectrolyzerRecipe.class);

    private static final int WIDTH  = 177;
    private static final int HEIGHT = 90;

    private final IDrawable background;
    private final IDrawable icon;

    public ElectrolyzerCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(WIDTH, HEIGHT);
        IDrawable rawIcon = guiHelper.createDrawableItemStack(
            new ItemStack(CreateMixAndCleanModBlocks.ELECTROLYZER.get()));

        icon = new IDrawable() {
            @Override public int getWidth()  { return rawIcon.getWidth(); }
            @Override public int getHeight() { return rawIcon.getHeight(); }
            @Override public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
                rawIcon.draw(graphics, xOffset, yOffset + 2);
            }
        };
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

    private IDrawable getChanceSlot(float chance) {
        if (chance >= 1.0f) {
            return CreateRecipeCategory.getRenderedSlot();
        }
        return new IDrawable() {
            @Override
            public int getWidth() { return 18; }

            @Override
            public int getHeight() { return 18; }

            @Override
            public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
                AllGuiTextures.JEI_CHANCE_SLOT.render(graphics, xOffset, yOffset);
            }
        };
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,
                          ElectrolyzerRecipe recipe,
                          IFocusGroup focuses) {

        List<ElectrolyzerRecipe.SizedIngredient> ingredients = recipe.getSizedIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            ElectrolyzerRecipe.SizedIngredient sizedIng = ingredients.get(i);
            
            List<ItemStack> sizedStacks = new ArrayList<>();
            for (ItemStack stack : sizedIng.getIngredient().getItems()) {
                ItemStack copy = stack.copy();
                copy.setCount(sizedIng.getCount());
                sizedStacks.add(copy);
            }

            builder.addSlot(RecipeIngredientRole.INPUT, 40, 40 + i * 19)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addItemStacks(sizedStacks);
        }

        List<FluidStack> fluids = recipe.getFluidIngredients();
        for (int i = 0; i < fluids.size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 20, 40 + i * 19)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, fluids.get(i))
                    .setFluidRenderer(fluids.get(i).getAmount(), false, 16, 16);
        }

        int outIndex = 0;
        
        List<ElectrolyzerRecipe.ChanceResult> results = recipe.getChanceResults();
        for (int i = 0; i < results.size(); i++) {
            ElectrolyzerRecipe.ChanceResult res = results.get(i);
            
            int xOut = 133 + (outIndex % 2) * 19;
            int yOut = 40 + (outIndex / 2) * 19;
            
            builder.addSlot(RecipeIngredientRole.OUTPUT, xOut, yOut)
                    .setBackground(getChanceSlot(res.getChance()), -1, -1)
                    .addItemStack(res.getStack())
                    .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                        if (res.getChance() < 1.0f) {
                            tooltip.add(Component.literal(String.format("Chance: %.0f%%", res.getChance() * 100))
                                    .withStyle(ChatFormatting.GOLD));
                        }
                    });
            outIndex++;
        }

        List<FluidStack> fluidResults = recipe.getFluidResults();
        for (int i = 0; i < fluidResults.size(); i++) {
            
            int xOut = 133 + (outIndex % 2) * 19;
            int yOut = 40 + (outIndex / 2) * 19;
            
            builder.addSlot(RecipeIngredientRole.OUTPUT, xOut, yOut)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, fluidResults.get(i))
                    .setFluidRenderer(fluidResults.get(i).getAmount(), false, 16, 16);
            outIndex++;
        }
    }

    @Override
    public void draw(ElectrolyzerRecipe recipe,
                     IRecipeSlotsView recipeSlotsView,
                     GuiGraphics graphics,
                     double mouseX, double mouseY) {

        AllGuiTextures.JEI_SHADOW.render(graphics, 78, 64);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 128, 15);

        AnimatedElectrolyzer.INSTANCE.draw(graphics, getBackground().getWidth() / 2, 32);
    }
}