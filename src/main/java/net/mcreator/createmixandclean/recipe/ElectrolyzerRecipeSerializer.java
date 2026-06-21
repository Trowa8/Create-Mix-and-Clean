package net.mcreator.createmixandclean.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class ElectrolyzerRecipeSerializer
        implements RecipeSerializer<ElectrolyzerRecipe> {

    public static final Codec<FluidStack> INGREDIENT_FLUID_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("fluid").forGetter(fs -> BuiltInRegistries.FLUID.getKey(fs.getFluid())),
            Codec.INT.optionalFieldOf("amount", 1000).forGetter(FluidStack::getAmount)
    ).apply(inst, (id, amount) -> new FluidStack(BuiltInRegistries.FLUID.get(id), amount)));

    public static final Codec<FluidStack> RESULT_FLUID_CODEC = FluidStack.CODEC;

    public static final Codec<ElectrolyzerRecipe.SizedIngredient> FLAT_ITEM_INGREDIENT_CODEC = Codec.PASSTHROUGH.flatXmap(
            dynamic -> {
                int count = dynamic.get("count").asInt(1);
                return Ingredient.CODEC_NONEMPTY.parse(dynamic)
                        .map(ing -> new ElectrolyzerRecipe.SizedIngredient(ing, count));
            },
            sizedIng -> Ingredient.CODEC_NONEMPTY.encodeStart(JsonOps.INSTANCE, sizedIng.getIngredient())
                    .map(elem -> {
                        if (elem.isJsonObject() && sizedIng.getCount() > 1) {
                            elem.getAsJsonObject().addProperty("count", sizedIng.getCount());
                        }
                        return new Dynamic<>(JsonOps.INSTANCE, elem);
                    })
    );

    public static final Codec<ElectrolyzerRecipe.ChanceResult> FLAT_CHANCE_RESULT_CODEC = Codec.PASSTHROUGH.flatXmap(
            dynamic -> {
                float chance = dynamic.get("chance").asFloat(1.0f);
                return ItemStack.CODEC.parse(dynamic)
                        .map(stack -> new ElectrolyzerRecipe.ChanceResult(stack, chance));
            },
            result -> ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, result.getStack())
                    .map(elem -> {
                        if (elem.isJsonObject() && result.getChance() < 1.0f) {
                            elem.getAsJsonObject().addProperty("chance", result.getChance());
                        }
                        return new Dynamic<>(JsonOps.INSTANCE, elem);
                    })
    );

    public static final Codec<Either<FluidStack, ElectrolyzerRecipe.SizedIngredient>> MIXED_INGREDIENT_CODEC = Codec.either(INGREDIENT_FLUID_CODEC, FLAT_ITEM_INGREDIENT_CODEC);
    public static final Codec<Either<FluidStack, ElectrolyzerRecipe.ChanceResult>> MIXED_RESULT_CODEC = Codec.either(RESULT_FLUID_CODEC, FLAT_CHANCE_RESULT_CODEC);

    public static final MapCodec<ElectrolyzerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            MIXED_INGREDIENT_CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> {
                List<Either<FluidStack, ElectrolyzerRecipe.SizedIngredient>> list = new ArrayList<>();
                recipe.getFluidIngredients().forEach(f -> list.add(Either.left(f)));
                recipe.getSizedIngredients().forEach(i -> list.add(Either.right(i)));
                return list;
            }),
            MIXED_RESULT_CODEC.listOf().fieldOf("results").forGetter(recipe -> {
                List<Either<FluidStack, ElectrolyzerRecipe.ChanceResult>> list = new ArrayList<>();
                recipe.getFluidResults().forEach(f -> list.add(Either.left(f)));
                recipe.getChanceResults().forEach(i -> list.add(Either.right(i)));
                return list;
            }),
            Codec.INT.optionalFieldOf("processingTime", 200).forGetter(ElectrolyzerRecipe::getProcessingTime)
    ).apply(inst, (ingredients, results, time) -> {
        
        List<ElectrolyzerRecipe.SizedIngredient> itemIngs = new ArrayList<>();
        List<FluidStack> fluidIngs = new ArrayList<>();
        for (var either : ingredients) {
            either.ifLeft(fluidIngs::add).ifRight(itemIngs::add);
        }

        List<ElectrolyzerRecipe.ChanceResult> itemRes = new ArrayList<>();
        List<FluidStack> fluidRes = new ArrayList<>();
        for (var either : results) {
            either.ifLeft(fluidRes::add).ifRight(itemRes::add);
        }

        return new ElectrolyzerRecipe(itemIngs, fluidIngs, itemRes, fluidRes, time);
    }));

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectrolyzerRecipe.SizedIngredient> SIZED_INGREDIENT_STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, ElectrolyzerRecipe.SizedIngredient::getIngredient,
            ByteBufCodecs.VAR_INT, ElectrolyzerRecipe.SizedIngredient::getCount,
            ElectrolyzerRecipe.SizedIngredient::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectrolyzerRecipe.ChanceResult> CHANCE_RESULT_STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ElectrolyzerRecipe.ChanceResult::getStack,
            ByteBufCodecs.FLOAT, ElectrolyzerRecipe.ChanceResult::getChance,
            ElectrolyzerRecipe.ChanceResult::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectrolyzerRecipe> STREAM_CODEC = StreamCodec.composite(
            SIZED_INGREDIENT_STREAM_CODEC.apply(ByteBufCodecs.list()), ElectrolyzerRecipe::getSizedIngredients,
            FluidStack.STREAM_CODEC.apply(ByteBufCodecs.list()), ElectrolyzerRecipe::getFluidIngredients,
            CHANCE_RESULT_STREAM_CODEC.apply(ByteBufCodecs.list()), ElectrolyzerRecipe::getChanceResults,
            FluidStack.STREAM_CODEC.apply(ByteBufCodecs.list()), ElectrolyzerRecipe::getFluidResults,
            ByteBufCodecs.VAR_INT, ElectrolyzerRecipe::getProcessingTime,
            ElectrolyzerRecipe::new
    );

    @Override
    public MapCodec<ElectrolyzerRecipe> codec() {return CODEC;}
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ElectrolyzerRecipe> streamCodec() {return STREAM_CODEC;}
}