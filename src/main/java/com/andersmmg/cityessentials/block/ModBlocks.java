package com.andersmmg.cityessentials.block;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.block.custom.CashRegisterBlock;
import com.andersmmg.cityessentials.block.custom.MailboxBlock;
import com.andersmmg.cityessentials.item.ModItemGroups;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block CASH_REGISTER = registerBlock("cash_register",
            new CashRegisterBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block MAILBOX = registerBlock("mailbox",
            new MailboxBlock(FabricBlockSettings.copyOf(Blocks.WHITE_CONCRETE).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, CityEssentials.id(name), block);
    }

    private static Block registerBlockOnly(String name, Block block) {
        return Registry.register(Registries.BLOCK, CityEssentials.id(name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, CityEssentials.id(name),
                new BlockItem(block, new OwoItemSettings().group(ModItemGroups.CITYESSENTIALS_GROUP)));
    }

    public static void registerModBlocks() {
        CityEssentials.LOGGER.info("Registering ModBlocks for " + CityEssentials.MOD_ID);
    }
}