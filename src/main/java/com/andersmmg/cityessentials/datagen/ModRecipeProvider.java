package com.andersmmg.cityessentials.datagen;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        generateConversionRecipe(exporter, ModItems.CASH_1, ModItems.CASH_5, 5, 1, "cash_1_to_5");
        generateConversionRecipe(exporter, ModItems.CASH_5, ModItems.CASH_10, 2, 1, "cash_5_to_10");
        generateConversionRecipe(exporter, ModItems.CASH_5, ModItems.CASH_1, 1, 5, "cash_5_to_1");
        generateConversionRecipe(exporter, ModItems.CASH_10, ModItems.CASH_5, 1, 2, "cash_10_to_5");
        generateConversionRecipe(exporter, ModItems.CASH_10, ModItems.CASH_20, 2, 1, "cash_10_to_20");
        generateConversionRecipe(exporter, ModItems.CASH_20, ModItems.CASH_10, 1, 2, "cash_20_to_10");
    }

    private void generateConversionRecipe(Consumer<RecipeJsonProvider> exporter, Item from, Item to, int fromCount, int toCount, String id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, to, toCount)
                .input(from, fromCount)
                .criterion(FabricRecipeProvider.hasItem(from), FabricRecipeProvider.conditionsFromItem(from))
                .criterion(FabricRecipeProvider.hasItem(to), FabricRecipeProvider.conditionsFromItem(to))
                .offerTo(exporter, CityEssentials.id(id));
    }
}