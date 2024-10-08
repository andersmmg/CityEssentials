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
    public static final SoundEvent MAILBOX_OPEN = registerSoundEvent("mailbox_open");
    public static final SoundEvent MAILBOX_CLOSE = registerSoundEvent("mailbox_close");
    public static final SoundEvent ENVELOPE_OPEN = registerSoundEvent("envelope_open");
    public static final SoundEvent BELL_DING = registerSoundEvent("bell_ding");
    public static final SoundEvent WALLET_OPEN = registerSoundEvent("wallet_open");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = CityEssentials.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        CityEssentials.LOGGER.info("Registering Mod Sounds for " + CityEssentials.MOD_ID);
    }
}
