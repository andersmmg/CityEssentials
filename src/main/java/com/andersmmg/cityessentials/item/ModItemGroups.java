package com.andersmmg.cityessentials.item;


import com.andersmmg.cityessentials.CityEssentials;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;

public class ModItemGroups {
    public static final OwoItemGroup CITYESSENTIALS_GROUP = OwoItemGroup
            .builder(CityEssentials.id("fallout_stuff"), () -> Icon.of(ModItems.PAPER_BAG))
            .build();

    public static void registerItemGroups() {
        CityEssentials.LOGGER.info("Registering Mod Item Groups for " + CityEssentials.MOD_ID);
        CITYESSENTIALS_GROUP.initialize();
    }
}
