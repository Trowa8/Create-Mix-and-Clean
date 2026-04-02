package main.java.net.mcreator.createmixandclean.recipe;

import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ElectrolyzerRecipeSerializer
        implements RecipeSerializer<ElectrolyzerRecipe> {

    @Override
    public ElectrolyzerRecipe fromJson(ResourceLocation id, JsonObject json) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (JsonElement el : GsonHelper.getAsJsonArray(json, "ingredients")) {
            ingredients.add(Ingredient.fromJson(el));
        }

        List<ItemStack> results = new ArrayList<>();
        for (JsonElement el : GsonHelper.getAsJsonArray(json, "results")) {
            JsonObject obj    = el.getAsJsonObject();
            ResourceLocation itemId = new ResourceLocation(GsonHelper.getAsString(obj, "item"));
            int count = GsonHelper.getAsInt(obj, "count", 1);
            ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count);
            results.add(stack);
        }

        int processingTime = GsonHelper.getAsInt(json, "processingTime", 200);
        return new ElectrolyzerRecipe(id, ingredients, results, processingTime);
    }

    @Nullable
    @Override
    public ElectrolyzerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        int ingCount = buf.readVarInt();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(ingCount, Ingredient.EMPTY);
        for (int i = 0; i < ingCount; i++)
            ingredients.set(i, Ingredient.fromNetwork(buf));

        int resCount = buf.readVarInt();
        List<ItemStack> results = new ArrayList<>();
        for (int i = 0; i < resCount; i++)
            results.add(buf.readItem());

        int time = buf.readVarInt();
        return new ElectrolyzerRecipe(id, ingredients, results, time);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ElectrolyzerRecipe recipe) {
        buf.writeVarInt(recipe.getIngredients().size());
        for (Ingredient ing : recipe.getIngredients())
            ing.toNetwork(buf);

        buf.writeVarInt(recipe.getResults().size());
        for (ItemStack stack : recipe.getResults())
            buf.writeItem(stack);

        buf.writeVarInt(recipe.getProcessingTime());
    }
}