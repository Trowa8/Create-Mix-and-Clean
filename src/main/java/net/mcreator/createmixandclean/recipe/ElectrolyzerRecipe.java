package net.mcreator.createmixandclean.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ElectrolyzerRecipe implements Recipe<Container> {

    public static class SizedIngredient {
        private final Ingredient ingredient;
        private final int count;
        public SizedIngredient(Ingredient ingredient, int count) {
            this.ingredient = ingredient;
            this.count = count;
        }
        public Ingredient getIngredient() { return ingredient; }
        public int getCount() { return count; }
    }

    public static class ChanceResult {
        private final ItemStack stack;
        private final float chance;
        public ChanceResult(ItemStack stack, float chance) {
            this.stack = stack;
            this.chance = chance;
        }
        public ItemStack getStack() { return stack; }
        public float getChance() { return chance; }
    }

    public static RecipeType<ElectrolyzerRecipe>       TYPE;
    public static RecipeSerializer<ElectrolyzerRecipe> SERIALIZER;

    private final ResourceLocation            id;
    private final List<SizedIngredient>       ingredients;
    private final List<FluidStack>            fluidIngredients;
    private final List<ChanceResult>          results;
    private final int                         processingTime;
    private final List<FluidStack>            fluidResults;

    public ElectrolyzerRecipe(ResourceLocation id,
                               List<SizedIngredient> ingredients,
                               List<FluidStack> fluidIngredients,
                               List<ChanceResult> results,
                               List<FluidStack> fluidResults,
                               int processingTime) {
        this.id               = id;
        this.ingredients      = ingredients;
        this.fluidIngredients = fluidIngredients;
        this.results          = results;
        this.fluidResults     = fluidResults;
        this.processingTime   = processingTime;
    }

    public boolean matchesInventory(IItemHandler inv) {
        List<ItemStack> available = new ArrayList<>();
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack s = inv.getStackInSlot(i).copy();
            if (!s.isEmpty()) available.add(s);
        }
        
        for (SizedIngredient sizedIng : ingredients) {
            int required = sizedIng.getCount();
            for (ItemStack slot : available) {
                if (sizedIng.getIngredient().test(slot)) {
                    int extracted = Math.min(slot.getCount(), required);
                    slot.shrink(extracted);
                    required -= extracted;
                    if (required <= 0) break;
                }
            }
            if (required > 0) return false;
        }
        return true;
    }

    public boolean matchesFluids(@Nullable IFluidHandler tank) {
        if (fluidIngredients.isEmpty()) return true;
        if (tank == null) return false;
        for (FluidStack required : fluidIngredients) {
            boolean found = false;
            for (int i = 0; i < tank.getTanks(); i++) {
                FluidStack inTank = tank.getFluidInTank(i);
                if (inTank.getFluid() == required.getFluid()
                        && inTank.getAmount() >= required.getAmount()) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public void consumeIngredients(IItemHandler inv) {
        for (SizedIngredient sizedIng : ingredients) {
            int required = sizedIng.getCount();
            for (int i = 0; i < inv.getSlots(); i++) {
                ItemStack slot = inv.getStackInSlot(i);
                if (!slot.isEmpty() && sizedIng.getIngredient().test(slot)) {
                    int extracted = Math.min(slot.getCount(), required);
                    inv.extractItem(i, extracted, false);
                    required -= extracted;
                    if (required <= 0) break;
                }
            }
        }
    }

    public void depositResults(IItemHandler inv) {
        for (ChanceResult result : results) {
            if (Math.random() > result.getChance()) continue; 
            
            ItemStack toInsert = result.getStack().copy();
            for (int i = 0; i < inv.getSlots(); i++) {
                toInsert = inv.insertItem(i, toInsert, false);
                if (toInsert.isEmpty()) break;
            }
        }
    }
    
    public void depositFluidResults(IFluidHandler tank) {
        for (FluidStack result : fluidResults) {
            tank.fill(result.copy(), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override public boolean matches(Container c, Level l) { return false; }
    @Override public ItemStack assemble(Container container, net.minecraft.core.RegistryAccess registryAccess) { return ItemStack.EMPTY; }
    @Override public boolean canCraftInDimensions(int w, int h) { return true; }
    @Override public ItemStack getResultItem(net.minecraft.core.RegistryAccess a) { return results.isEmpty() ? ItemStack.EMPTY : results.get(0).getStack(); }
    @Override public ResourceLocation getId()            { return id; }
    @Override public RecipeSerializer<?> getSerializer() { return SERIALIZER; }
    @Override public RecipeType<?> getType()             { return TYPE; }
    
    @Override 
    public NonNullList<Ingredient> getIngredients() { 
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        for (SizedIngredient sizedIng : ingredients) nonNullList.add(sizedIng.getIngredient());
        return nonNullList; 
    }

    public int getProcessingTime()            { return processingTime; }
    public List<ChanceResult> getChanceResults()       { return results; }
    public List<SizedIngredient> getSizedIngredients() { return ingredients; }
    public List<FluidStack> getFluidIngredients() { return fluidIngredients; }
    public List<FluidStack> getFluidResults() { return fluidResults; }
}