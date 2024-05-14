package com.andersmmg.cityessentials.sounds;

import com.andersmmg.cityessentials.CityEssentials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CASH_REGISTER_OPEN = registerSoundEvent("cash_register_open");
    public static final SoundEvent CASH_REGISTER_CLOSE = registerSoundEvent("cash_register_close");
    public static final SoundEvent PAPER_BAG_OPEN = registerSoundEvent("paper_bag_open");
    public static final SoundEvent GROCERY_BAG_OPEN = registerSoundEvent("grocery_bag_open");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = CityEssentials.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        CityEssentials.LOGGER.info("Registering Mod Sounds for " + CityEssentials.MOD_ID);
    }
}
