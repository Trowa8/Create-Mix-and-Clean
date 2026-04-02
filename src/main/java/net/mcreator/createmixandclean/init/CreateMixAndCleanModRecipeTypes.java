package net.mcreator.createmixandclean.init;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipe;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreateMixAndCleanModRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,
                    CreateMixAndCleanMod.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES,
                    CreateMixAndCleanMod.MODID);

    public static final RegistryObject<RecipeType<ElectrolyzerRecipe>> ELECTROLYZING =
            TYPES.register("electrolyzing", () -> RecipeType.simple(
                    new net.minecraft.resources.ResourceLocation(
                            CreateMixAndCleanMod.MODID, "electrolyzing")));

    public static final RegistryObject<RecipeSerializer<ElectrolyzerRecipe>> ELECTROLYZING_SERIALIZER =
            SERIALIZERS.register("electrolyzing", ElectrolyzerRecipeSerializer::new);
}