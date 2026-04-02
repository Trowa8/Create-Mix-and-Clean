package main.java.net.mcreator.createmixandclean.recipe;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class ElectrolyzerRecipe implements Recipe<Container> {

    // ── Static type references (set during registration) ─────────
    public static RecipeType<ElectrolyzerRecipe>       TYPE;
    public static RecipeSerializer<ElectrolyzerRecipe> SERIALIZER;

    // ── Fields ───────────────────────────────────────────────────
    private final ResourceLocation    id;
    private final NonNullList<Ingredient> ingredients;
    private final List<ItemStack>     results;
    private final int                 processingTime;

    public ElectrolyzerRecipe(ResourceLocation id,
                               NonNullList<Ingredient> ingredients,
                               List<ItemStack> results,
                               int processingTime) {
        this.id             = id;
        this.ingredients    = ingredients;
        this.results        = results;
        this.processingTime = processingTime;
    }

    // ── Core matching ─────────────────────────────────────────────
    /**
     * Check whether the basin's IItemHandler contains all required ingredients.
     * Uses a copy-of-slots approach to handle duplicate ingredients correctly.
     */
    public boolean matchesInventory(IItemHandler inv) {
        // Build a mutable snapshot of item counts in the basin
        List<ItemStack> available = new java.util.ArrayList<>();
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack s = inv.getStackInSlot(i).copy();
            if (!s.isEmpty()) available.add(s);
        }

        outer:
        for (Ingredient ingredient : ingredients) {
            for (ItemStack slot : available) {
                if (ingredient.test(slot)) {
                    slot.shrink(1);
                    continue outer;
                }
            }
            return false; // ingredient not satisfied
        }
        return true;
    }

    /** Consume one of each ingredient from the basin inventory. */
    public void consumeIngredients(IItemHandler inv) {
        for (Ingredient ingredient : ingredients) {
            for (int i = 0; i < inv.getSlots(); i++) {
                ItemStack slot = inv.getStackInSlot(i);
                if (!slot.isEmpty() && ingredient.test(slot)) {
                    inv.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }

    /** Insert result items into the first available slots. */
    public void depositResults(IItemHandler inv) {
        for (ItemStack result : results) {
            ItemStack toInsert = result.copy();
            for (int i = 0; i < inv.getSlots(); i++) {
                toInsert = inv.insertItem(i, toInsert, false);
                if (toInsert.isEmpty()) break;
            }
            // If toInsert is still non-empty, the basin is full — drop it
            if (!toInsert.isEmpty()) {
                // Optional: drop item in world at basin pos
            }
        }
    }

    // ── Recipe interface (mostly unused for our manual system) ────
    @Override
    public boolean matches(Container pContainer, Level pLevel) { return false; }
    @Override
    public ItemStack assemble(Container pContainer, net.minecraft.world.item.crafting.RecipeManager.CachedCheck<Container, ?> check) { return ItemStack.EMPTY; }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) { return true; }
    @Override
    public ItemStack getResultItem(net.minecraft.core.RegistryAccess access) {
        return results.isEmpty() ? ItemStack.EMPTY : results.get(0);
    }
    @Override
    public ResourceLocation getId() { return id; }
    @Override
    public RecipeSerializer<?> getSerializer() { return SERIALIZER; }
    @Override
    public RecipeType<?> getType() { return TYPE; }
    @Override
    public NonNullList<Ingredient> getIngredients() { return ingredients; }

    // ── Getters ───────────────────────────────────────────────────
    public int getProcessingTime() { return processingTime; }
    public List<ItemStack> getResults() { return results; }
}