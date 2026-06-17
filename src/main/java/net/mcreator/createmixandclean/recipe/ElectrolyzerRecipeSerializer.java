package net.mcreator.createmixandclean.recipe;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ElectrolyzerRecipeSerializer
        implements RecipeSerializer<ElectrolyzerRecipe> {

    @Override
    public ElectrolyzerRecipe fromJson(ResourceLocation id, JsonObject json) {
        List<ElectrolyzerRecipe.SizedIngredient> ingredients = new ArrayList<>();
        List<FluidStack> fluidIngredients = new ArrayList<>();

        for (JsonElement el : GsonHelper.getAsJsonArray(json, "ingredients")) {
            JsonObject obj = el.getAsJsonObject();
            if (obj.has("fluid")) {
                ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(obj, "fluid"));
                int amount = GsonHelper.getAsInt(obj, "amount", 1000);
                var fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
                if (fluid != null)
                    fluidIngredients.add(new FluidStack(fluid, amount));
            } else {
                int count = GsonHelper.getAsInt(obj, "count", 1);
                ingredients.add(new ElectrolyzerRecipe.SizedIngredient(Ingredient.fromJson(el), count));
            }
        }

        List<ElectrolyzerRecipe.ChanceResult> results = new ArrayList<>();
        List<FluidStack> fluidResults = new ArrayList<>();
        for (JsonElement el : GsonHelper.getAsJsonArray(json, "results")) {
            JsonObject obj = el.getAsJsonObject();
            if (obj.has("fluid")) {
                ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(obj, "fluid"));
                int amount = GsonHelper.getAsInt(obj, "amount", 1000);
                var fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
                if (fluid != null)
                    fluidResults.add(new FluidStack(fluid, amount));
            } else {
                ResourceLocation itemId = new ResourceLocation(GsonHelper.getAsString(obj, "item"));
                int count = GsonHelper.getAsInt(obj, "count", 1);
                float chance = GsonHelper.getAsFloat(obj, "chance", 1.0f);
                results.add(new ElectrolyzerRecipe.ChanceResult(new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count), chance));
            }
        }

        int processingTime = GsonHelper.getAsInt(json, "processingTime", 200);
        return new ElectrolyzerRecipe(id, ingredients, fluidIngredients, results, fluidResults, processingTime);
    }

    @Nullable
    @Override
    public ElectrolyzerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        int ingCount = buf.readVarInt();
        List<ElectrolyzerRecipe.SizedIngredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingCount; i++) {
            ingredients.add(new ElectrolyzerRecipe.SizedIngredient(Ingredient.fromNetwork(buf), buf.readVarInt()));
        }

        int fluidCount = buf.readVarInt();
        List<FluidStack> fluidIngredients = new ArrayList<>();
        for (int i = 0; i < fluidCount; i++)
            fluidIngredients.add(FluidStack.readFromPacket(buf));

        int resCount = buf.readVarInt();
        List<ElectrolyzerRecipe.ChanceResult> results = new ArrayList<>();
        for (int i = 0; i < resCount; i++) {
            results.add(new ElectrolyzerRecipe.ChanceResult(buf.readItem(), buf.readFloat()));
        }

        int fluidResCount = buf.readVarInt();
        List<FluidStack> fluidResults = new ArrayList<>();
        for (int i = 0; i < fluidResCount; i++)
            fluidResults.add(FluidStack.readFromPacket(buf));
            
        int processingTime = buf.readVarInt();

        return new ElectrolyzerRecipe(id, ingredients, fluidIngredients, results, fluidResults, processingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ElectrolyzerRecipe recipe) {
        buf.writeVarInt(recipe.getSizedIngredients().size());
        for (ElectrolyzerRecipe.SizedIngredient ing : recipe.getSizedIngredients()) {
            ing.getIngredient().toNetwork(buf);
            buf.writeVarInt(ing.getCount());
        }

        buf.writeVarInt(recipe.getFluidIngredients().size());
        for (FluidStack fs : recipe.getFluidIngredients())
            fs.writeToPacket(buf);

        buf.writeVarInt(recipe.getChanceResults().size());
        for (ElectrolyzerRecipe.ChanceResult result : recipe.getChanceResults()) {
            buf.writeItem(result.getStack());
            buf.writeFloat(result.getChance());
        }

        buf.writeVarInt(recipe.getFluidResults().size());
        for (FluidStack fs : recipe.getFluidResults())
            fs.writeToPacket(buf);     
            
        buf.writeVarInt(recipe.getProcessingTime());
    }
}