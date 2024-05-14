package com.andersmmg.cityessentials.config;

import com.andersmmg.cityessentials.CityEssentials;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = CityEssentials.MOD_ID)
@Config(name = CityEssentials.MOD_ID, wrapperName = "ModConfig")
public class ModConfigModel {
    public boolean testBoolean = true;
}