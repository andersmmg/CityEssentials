package com.andersmmg.cityessentials.client;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.entity.ModBlockEntities;
import com.andersmmg.cityessentials.client.renderer.MailboxBlockEntityRenderer;
import com.andersmmg.cityessentials.client.renderer.SpeedLimitSignBlockEntityRenderer;
import com.andersmmg.cityessentials.client.renderer.StopSignBlockEntityRenderer;
import com.andersmmg.cityessentials.client.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class CityEssentialsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenHandlers.registerScreenHandlers();
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_POST, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STOP_SIGN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPEED_LIMIT_SIGN, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.MAILBOX_BLOCK_ENTITY, MailboxBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STOP_SIGN_BLOCK_ENTITY, StopSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.SPEED_LIMIT_SIGN_BLOCK_ENTITY, SpeedLimitSignBlockEntityRenderer::new);
    }
}
