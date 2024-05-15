package com.andersmmg.cityessentials.datagen;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.custom.CashRegisterBlock;
import com.andersmmg.cityessentials.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerOpenableBlock(blockStateModelGenerator, ModBlocks.CASH_REGISTER);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.PAPER_BAG, Models.GENERATED);
        itemModelGenerator.register(ModItems.GROCERY_BAG, Models.GENERATED);

        itemModelGenerator.register(ModItems.CASH_1, Models.GENERATED);
        itemModelGenerator.register(ModItems.CASH_5, Models.GENERATED);
        itemModelGenerator.register(ModItems.CASH_10, Models.GENERATED);
        itemModelGenerator.register(ModItems.CASH_20, Models.GENERATED);
    }

    private void registerOpenableBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier model_base = ModelIds.getBlockModelId(block);
        Identifier model_open = ModelIds.getBlockSubModelId(block, "_open");
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(CashRegisterBlock.OPEN, model_open, model_base)));
    }
}