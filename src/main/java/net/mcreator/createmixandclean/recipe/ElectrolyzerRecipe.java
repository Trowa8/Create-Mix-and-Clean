package net.mcreator.createmixandclean.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ElectrolyzerRecipe implements Recipe<Container> {

    public static RecipeType<ElectrolyzerRecipe>       TYPE;
    public static RecipeSerializer<ElectrolyzerRecipe> SERIALIZER;

    private final ResourceLocation            id;
    private final NonNullList<Ingredient>     ingredients;
    private final List<ItemStack>             results;
    private final int                         processingTime;

    public ElectrolyzerRecipe(ResourceLocation id,
                               NonNullList<Ingredient> ingredients,
                               List<ItemStack> results,
                               int processingTime) {
        this.id             = id;
        this.ingredients    = ingredients;
        this.results        = results;
        this.processingTime = processingTime;
    }

    public boolean matchesInventory(IItemHandler inv) {
        List<ItemStack> available = new ArrayList<>();
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
            return false;
        }
        return true;
    }

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

    public void depositResults(IItemHandler inv) {
        for (ItemStack result : results) {
            ItemStack toInsert = result.copy();
            for (int i = 0; i < inv.getSlots(); i++) {
                toInsert = inv.insertItem(i, toInsert, false);
                if (toInsert.isEmpty()) break;
            }
        }
    }

    @Override public boolean matches(Container c, Level l) { return false; }
    @Override
    public ItemStack assemble(Container container, net.minecraft.core.RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }
    @Override public boolean canCraftInDimensions(int w, int h) { return true; }
    @Override public ItemStack getResultItem(net.minecraft.core.RegistryAccess a) {
        return results.isEmpty() ? ItemStack.EMPTY : results.get(0);
    }
    @Override public ResourceLocation getId()               { return id; }
    @Override public RecipeSerializer<?> getSerializer()    { return SERIALIZER; }
    @Override public RecipeType<?> getType()                { return TYPE; }
    @Override public NonNullList<Ingredient> getIngredients() { return ingredients; }

    public int getProcessingTime()   { return processingTime; }
    public List<ItemStack> getResults() { return results; }
}