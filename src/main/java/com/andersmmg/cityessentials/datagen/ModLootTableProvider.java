package com.andersmmg.cityessentials.datagen;

import com.andersmmg.cityessentials.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CASH_REGISTER);
        addDrop(ModBlocks.MAILBOX);
        addDrop(ModBlocks.EXIT_SIGN);
        addDrop(ModBlocks.LAMP_POST);
        addDrop(ModBlocks.STREET_LAMP);
        addDrop(ModBlocks.DESK_BELL);
        addDrop(ModBlocks.SIGN_POST);
        addDrop(ModBlocks.STOP_SIGN);
        addDrop(ModBlocks.SPEED_LIMIT_SIGN);
        addDrop(ModBlocks.MAIL_DROPBOX);
        addDrop(ModBlocks.OPEN_CLOSED_SIGN);
        addDrop(ModBlocks.DOOR_SENSOR);
        addDrop(ModBlocks.STREET_SIGN);
    }

}