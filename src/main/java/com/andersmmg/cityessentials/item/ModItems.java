package com.andersmmg.cityessentials.item;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.item.custom.*;
import com.andersmmg.cityessentials.sounds.ModSounds;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item PAPER_BAG = registerItem("paper_bag", new BagItem(new OwoItemSettings().maxCount(1).group(ModItemGroups.CITYESSENTIALS_GROUP), ModSounds.PAPER_BAG_OPEN));
    public static final Item GROCERY_BAG = registerItem("grocery_bag", new BagItem(new OwoItemSettings().maxCount(1).group(ModItemGroups.CITYESSENTIALS_GROUP), ModSounds.GROCERY_BAG_OPEN));
    public static final Item ENVELOPE = registerItem("envelope", new EnvelopeItem(new OwoItemSettings().maxCount(1).group(ModItemGroups.CITYESSENTIALS_GROUP)));
    public static final Item WALLET = registerItem("wallet", new WalletItem(new OwoItemSettings().maxCount(1).group(ModItemGroups.CITYESSENTIALS_GROUP), ModSounds.WALLET_OPEN));

    public static final Item MARKER = registerItem("marker", new MarkerItem(new OwoItemSettings().maxCount(1).group(ModItemGroups.CITYESSENTIALS_GROUP)));

    public static final Item CASH_1 = registerItem("cash_1", new CashItem(new OwoItemSettings().group(ModItemGroups.CITYESSENTIALS_GROUP), 1));
    public static final Item CASH_5 = registerItem("cash_5", new CashItem(new OwoItemSettings().group(ModItemGroups.CITYESSENTIALS_GROUP), 5));
    public static final Item CASH_10 = registerItem("cash_10", new CashItem(new OwoItemSettings().group(ModItemGroups.CITYESSENTIALS_GROUP), 10));
    public static final Item CASH_20 = registerItem("cash_20", new CashItem(new OwoItemSettings().group(ModItemGroups.CITYESSENTIALS_GROUP), 20));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, CityEssentials.id(name), item);
    }

    public static void registerModItems() {
        CityEssentials.LOGGER.info("Registering Mod Items for " + CityEssentials.MOD_ID);
    }
}
