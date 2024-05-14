package com.andersmmg.cityessentials;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.entity.ModBlockEntities;
import com.andersmmg.cityessentials.config.ModConfig;
import com.andersmmg.cityessentials.item.ModItemGroups;
import com.andersmmg.cityessentials.item.ModItems;
import com.andersmmg.cityessentials.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityEssentials implements ModInitializer {
    public static final String MOD_ID = "cityessentials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ModConfig CONFIG = ModConfig.createAndLoad();

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
        ModBlocks.registerModBlocks();

        ModBlockEntities.registerBlockEntities();
        ModSounds.registerSounds();
    }
}
