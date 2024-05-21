package com.andersmmg.cityessentials.datagen;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.custom.ExitSignBlock;
import com.andersmmg.cityessentials.block.custom.MailboxBlock;
import com.andersmmg.cityessentials.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerOpenableBlock(blockStateModelGenerator, ModBlocks.CASH_REGISTER);
        registerMailbox(blockStateModelGenerator, ModBlocks.MAILBOX);
        registerBasicModel(blockStateModelGenerator, ModBlocks.SIGN_POST);
        registerRotatable(blockStateModelGenerator, ModBlocks.STOP_SIGN);
        registerRotatable(blockStateModelGenerator, ModBlocks.SPEED_LIMIT_SIGN);
        registerRotatable(blockStateModelGenerator, ModBlocks.MAIL_DROPBOX);
        registerRotatable(blockStateModelGenerator, ModBlocks.DOOR_SENSOR);
        registerExitSign(blockStateModelGenerator, ModBlocks.EXIT_SIGN);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.PAPER_BAG, Models.GENERATED);
        itemModelGenerator.register(ModItems.GROCERY_BAG, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENVELOPE, Models.GENERATED);

        itemModelGenerator.register(ModItems.MARKER, Models.GENERATED);

        itemModelGenerator.register(ModItems.CASH_1, Models.GENERATED);
        itemModelGenerator.register(ModItems.CASH_5, Models.GENERATED);
        itemModelGenerator.register(ModItems.CASH_10, Models.GENERATED);
        itemModelGenerator.register(ModItems.CASH_20, Models.GENERATED);
    }

    private void registerBasicModel(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier model_base = ModelIds.getBlockModelId(block);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, model_base)));
    }

    private void registerRotatable(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier model_base = ModelIds.getBlockModelId(block);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, model_base))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    private void registerExitSign(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier model_base = ModelIds.getBlockModelId(block);
        Identifier model_ceiling = ModelIds.getBlockSubModelId(block, "_ceiling");
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, model_base))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(ExitSignBlock.CEILING, model_ceiling, model_base)));
    }

    private void registerOpenableBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier model_base = ModelIds.getBlockModelId(block);
        Identifier model_open = ModelIds.getBlockSubModelId(block, "_open");
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.OPEN, model_open, model_base)));
    }

    private void registerMailbox(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        BlockStateVariantMap.DoubleProperty<Boolean, Boolean> doubleProperty = BlockStateVariantMap.create(MailboxBlock.FLAG, MailboxBlock.OPEN);

        doubleProperty.register(true, true, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(block, "_flag_open")));
        doubleProperty.register(true, false, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(block, "_flag")));
        doubleProperty.register(false, true, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(block, "_open")));
        doubleProperty.register(false, false, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(block)));

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(doubleProperty)
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }
}