package com.andersmmg.cityessentials.client.screen;

import com.andersmmg.cityessentials.CityEssentials;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static void registerScreenHandlers() {
        CityEssentials.LOGGER.info("Registering Screen Handlers for " + CityEssentials.MOD_ID);
        Registry.register(Registries.SCREEN_HANDLER, CityEssentials.id("bag"), BAG_SCREEN_HANDLER);
        HandledScreens.register(BAG_SCREEN_HANDLER, Generic3x3ContainerScreen::new);
    }    public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = new ScreenHandlerType<>(BagScreenHandler::new, FeatureFlags.VANILLA_FEATURES);


}