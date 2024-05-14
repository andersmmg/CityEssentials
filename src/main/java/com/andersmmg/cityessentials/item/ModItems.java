package com.andersmmg.cityessentials.item;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.item.custom.PaperBagItem;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item PAPER_BAG = registerItem("paper_bag", new PaperBagItem(new OwoItemSettings().maxCount(1).group(ModItemGroups.CITYESSENTIALS_GROUP)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, CityEssentials.id(name), item);
    }

    public static void registerModItems() {
        CityEssentials.LOGGER.info("Registering Mod Items for " + CityEssentials.MOD_ID);
    }
}
