package com.andersmmg.cityessentials.datagen;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PAPER_BAG, 1)
                .pattern("p p")
                .pattern(" p ")
                .input('p', Items.PAPER)
                .criterion("has_item", conditionsFromItem(Items.PAPER))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GROCERY_BAG, 1)
                .pattern("s s")
                .pattern("p p")
                .pattern(" p ")
                .input('s', Items.STRING)
                .input('p', Items.PAPER)
                .criterion("has_item", conditionsFromItem(Items.PAPER))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ENVELOPE, 1)
                .pattern(" p ")
                .pattern("p p")
                .pattern(" p ")
                .input('p', Items.PAPER)
                .criterion("has_item", conditionsFromItem(Items.PAPER))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WALLET, 1)
                .pattern("ll ")
                .pattern("l  ")
                .pattern("ll ")
                .input('l', Items.LEATHER)
                .criterion("has_item", conditionsFromItem(Items.LEATHER))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.MARKER, 1)
                .pattern("i")
                .pattern("s")
                .input('s', Items.STICK)
                .input('i', Items.INK_SAC)
                .criterion("has_item", conditionsFromItem(Items.INK_SAC))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.MAIL_DROPBOX, 1)
                .pattern("iii")
                .pattern("idi")
                .pattern("ibi")
                .input('i', Items.IRON_INGOT)
                .input('d', Items.BLUE_DYE)
                .input('b', Blocks.IRON_BLOCK)
                .criterion("has_item", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.DESK_BELL, 1)
                .pattern(" i ")
                .pattern("iai")
                .input('i', Items.IRON_INGOT)
                .input('a', Items.AMETHYST_SHARD)
                .criterion("has_item", conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.CASH_REGISTER, 1)
                .pattern("iii")
                .pattern("iai")
                .pattern("iii")
                .input('i', Items.IRON_INGOT)
                .input('a', Items.AMETHYST_SHARD)
                .criterion("has_item", conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.MAILBOX, 1)
                .pattern("iii")
                .pattern("i i")
                .pattern(" i ")
                .input('i', Items.IRON_INGOT)
                .criterion("has_item", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.SIGN_POST, 2)
                .pattern("i")
                .pattern("i")
                .input('i', Items.IRON_INGOT)
                .criterion("has_item", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.LAMP_POST, 2)
                .pattern(" i ")
                .pattern(" i ")
                .pattern("iri")
                .input('i', Items.IRON_INGOT)
                .input('r', Items.REDSTONE)
                .criterion("has_item", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.STREET_LAMP, 1)
                .pattern(" i ")
                .pattern("plp")
                .pattern(" i ")
                .input('i', Items.IRON_INGOT)
                .input('p', Blocks.GLASS_PANE)
                .input('l', Blocks.REDSTONE_LAMP)
                .criterion("has_item", conditionsFromItem(Blocks.REDSTONE_LAMP))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.DOOR_SENSOR, 1)
                .pattern("iai")
                .pattern(" r ")
                .input('i', Items.IRON_INGOT)
                .input('r', Items.REDSTONE)
                .input('a', Items.AMETHYST_SHARD)
                .criterion("has_item", conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.EXIT_SIGN, 1)
                .pattern(" i ")
                .pattern("igi")
                .input('i', Items.IRON_INGOT)
                .input('g', Items.GLOWSTONE_DUST)
                .criterion("has_item", conditionsFromItem(Items.GLOWSTONE_DUST))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.STOP_SIGN, 1)
                .pattern("nin")
                .pattern("idi")
                .pattern("nin")
                .input('i', Items.IRON_INGOT)
                .input('n', Items.IRON_NUGGET)
                .input('d', Items.RED_DYE)
                .criterion("has_item", conditionsFromItem(ModBlocks.SIGN_POST))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.SPEED_LIMIT_SIGN, 1)
                .pattern("nin")
                .pattern("idi")
                .pattern("nin")
                .input('i', Items.IRON_INGOT)
                .input('n', Items.IRON_NUGGET)
                .input('d', Items.WHITE_DYE)
                .criterion("has_item", conditionsFromItem(ModBlocks.SIGN_POST))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.STREET_SIGN, 1)
                .pattern(" n ")
                .pattern("idi")
                .pattern(" n ")
                .input('i', Items.IRON_INGOT)
                .input('n', Items.IRON_NUGGET)
                .input('d', Items.GREEN_DYE)
                .criterion("has_item", conditionsFromItem(ModBlocks.SIGN_POST))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.OPEN_CLOSED_SIGN, 1)
                .pattern(" s ")
                .pattern("nnn")
                .pattern("nnn")
                .input('s', Items.STRING)
                .input('n', Items.IRON_NUGGET)
                .criterion("has_item", conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void generateConversionRecipe(Consumer<RecipeJsonProvider> exporter, Item from, Item to, int fromCount, int toCount, String id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, to, toCount)
                .input(from, fromCount)
                .criterion(FabricRecipeProvider.hasItem(from), FabricRecipeProvider.conditionsFromItem(from))
                .criterion(FabricRecipeProvider.hasItem(to), FabricRecipeProvider.conditionsFromItem(to))
                .offerTo(exporter, CityEssentials.id(id));
    }
}