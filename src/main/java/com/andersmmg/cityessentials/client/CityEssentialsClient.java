package com.andersmmg.cityessentials.client;

import com.andersmmg.cityessentials.block.entity.ModBlockEntities;
import com.andersmmg.cityessentials.client.renderer.MailboxBlockEntityRenderer;
import com.andersmmg.cityessentials.client.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class CityEssentialsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenHandlers.registerScreenHandlers();

        BlockEntityRendererFactories.register(ModBlockEntities.MAILBOX_BLOCK_ENTITY, MailboxBlockEntityRenderer::new);
    }
}
