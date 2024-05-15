package com.andersmmg.cityessentials.block.entity;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static void registerBlockEntities() {
        CityEssentials.LOGGER.info("Registering Block Entities for " + CityEssentials.MOD_ID);
    }

    public static final BlockEntityType<CashRegisterBlockEntity> CASH_REGISTER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CityEssentials.id("cash_register_be"),
                    FabricBlockEntityTypeBuilder.create(CashRegisterBlockEntity::new,
                            ModBlocks.CASH_REGISTER).build());
    public static final BlockEntityType<MailboxBlockEntity> MAILBOX_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CityEssentials.id("mailbox_be"),
                    FabricBlockEntityTypeBuilder.create(MailboxBlockEntity::new,
                            ModBlocks.MAILBOX).build());


}
