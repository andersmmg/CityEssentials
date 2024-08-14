package com.andersmmg.cityessentials.datagen;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.custom.PackageBlock;
import com.andersmmg.cityessentials.block.entity.ModBlockEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.SetContentsLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CASH_REGISTER);
        addDrop(ModBlocks.MAILBOX);
        addDrop(ModBlocks.EXIT_SIGN);
        addDrop(ModBlocks.LAMP_POST);
        addDrop(ModBlocks.STREET_LAMP);
        addDrop(ModBlocks.DESK_BELL);
        addDrop(ModBlocks.SIGN_POST);
        addDrop(ModBlocks.STOP_SIGN);
        addDrop(ModBlocks.SPEED_LIMIT_SIGN);
        addDrop(ModBlocks.MAIL_DROPBOX);
        addDrop(ModBlocks.OPEN_CLOSED_SIGN);
        addDrop(ModBlocks.DOOR_SENSOR);
        addDrop(ModBlocks.STREET_SIGN);
    }

    public LootTable.Builder packageBlockDrops(Block drop) {
        return LootTable.builder().pool(this.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(((LeafEntry.Builder<?>) ((LeafEntry.Builder<?>) ItemEntry.builder(drop).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))).apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY).withOperation("Lock", "BlockEntityTag.Lock").withOperation("LootTable", "BlockEntityTag.LootTable").withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed"))).apply(SetContentsLootFunction.builder(ModBlockEntities.PACKAGE_ENTITY).withEntry(DynamicEntry.builder(PackageBlock.CONTENTS_DYNAMIC_DROP_ID))))));
    }

}