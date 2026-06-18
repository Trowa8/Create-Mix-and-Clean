package net.mcreator.createmixandclean.init;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipe;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class CreateMixAndCleanModRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER,
                    CreateMixAndCleanMod.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE,
                    CreateMixAndCleanMod.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ElectrolyzerRecipe>> ELECTROLYZING =
            TYPES.register("electrolysis", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(
                            CreateMixAndCleanMod.MODID, "electrolysis")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ElectrolyzerRecipe>> ELECTROLYZING_SERIALIZER =
            SERIALIZERS.register("electrolysis", ElectrolyzerRecipeSerializer::new);
}