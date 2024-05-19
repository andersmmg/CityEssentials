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
    public static final BlockEntityType<MailDropboxBlockEntity> MAIL_DROPBOX_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CityEssentials.id("mail_dropbox_be"),
                    FabricBlockEntityTypeBuilder.create(MailDropboxBlockEntity::new,
                            ModBlocks.MAIL_DROPBOX).build());
    public static final BlockEntityType<StopSignBlockEntity> STOP_SIGN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CityEssentials.id("stop_sign_be"),
                    FabricBlockEntityTypeBuilder.create(StopSignBlockEntity::new,
                            ModBlocks.STOP_SIGN).build());
    public static final BlockEntityType<SpeedLimitSignBlockEntity> SPEED_LIMIT_SIGN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CityEssentials.id("speed_limit_sign_be"),
                    FabricBlockEntityTypeBuilder.create(SpeedLimitSignBlockEntity::new,
                            ModBlocks.SPEED_LIMIT_SIGN).build());
    public static final BlockEntityType<DoorSensorBlockEntity> DOOR_SENSOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CityEssentials.id("door_sensor_be"),
                    FabricBlockEntityTypeBuilder.create(DoorSensorBlockEntity::new,
                            ModBlocks.DOOR_SENSOR).build());


}
