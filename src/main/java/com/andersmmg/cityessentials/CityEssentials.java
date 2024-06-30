package com.andersmmg.cityessentials;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.entity.EditableSign;
import com.andersmmg.cityessentials.block.entity.ModBlockEntities;
import com.andersmmg.cityessentials.client.screen.ModScreenHandlers;
import com.andersmmg.cityessentials.config.ModConfig;
import com.andersmmg.cityessentials.item.ModItemGroups;
import com.andersmmg.cityessentials.item.ModItems;
import com.andersmmg.cityessentials.record.EnvelopeSealPacket;
import com.andersmmg.cityessentials.record.SignUpdatePacket;
import com.andersmmg.cityessentials.sounds.ModSounds;
import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityEssentials implements ModInitializer {
    public static final String MOD_ID = "cityessentials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ModConfig CONFIG = ModConfig.createAndLoad();

    public static final OwoNetChannel SIGN_UPDATE_CHANNEL = OwoNetChannel.create(id("sign_update_channel"));
    public static final OwoNetChannel ENVELOPE_SEAL_CHANNEL = OwoNetChannel.create(id("envelope_seal_channel"));

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

        // Register screen handlers
        ModScreenHandlers.registerScreenHandlers();

        // Register sign update packet
        SIGN_UPDATE_CHANNEL.registerServerbound(SignUpdatePacket.class, (message, access) -> {
            World world = access.player().getServerWorld();
            BlockEntity blockEntity = world.getBlockEntity(message.pos());
            if (blockEntity instanceof EditableSign signBlockEntity) {
                signBlockEntity.setText(message.text());
            }
        });

        // Register envelope seal packet
        ENVELOPE_SEAL_CHANNEL.registerServerbound(EnvelopeSealPacket.class, (message, access) -> {
            ServerPlayerEntity player = access.player();
            player.getStackInHand(message.hand()).getOrCreateNbt().put("sender", NbtString.of(player.getEntityName()));
            player.getStackInHand(message.hand()).getOrCreateNbt().put("receiver", NbtString.of(message.receiver()));
        });
    }
}
