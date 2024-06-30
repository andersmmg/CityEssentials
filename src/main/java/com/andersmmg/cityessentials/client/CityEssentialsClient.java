package com.andersmmg.cityessentials.client;

import com.andersmmg.cityessentials.block.ModBlocks;
import com.andersmmg.cityessentials.block.entity.ModBlockEntities;
import com.andersmmg.cityessentials.client.renderer.MailboxBlockEntityRenderer;
import com.andersmmg.cityessentials.client.renderer.SpeedLimitSignBlockEntityRenderer;
import com.andersmmg.cityessentials.client.renderer.StopSignBlockEntityRenderer;
import com.andersmmg.cityessentials.client.renderer.StreetSignBlockEntityRenderer;
import com.andersmmg.cityessentials.client.screen.ModScreenHandlers;
import com.andersmmg.cityessentials.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class CityEssentialsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenHandlers.registerScreens();
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SIGN_POST, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STOP_SIGN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPEED_LIMIT_SIGN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXIT_SIGN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STREET_SIGN, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.MAILBOX_BLOCK_ENTITY, MailboxBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STOP_SIGN_BLOCK_ENTITY, StopSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.SPEED_LIMIT_SIGN_BLOCK_ENTITY, SpeedLimitSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STREET_SIGN_BLOCK_ENTITY, StreetSignBlockEntityRenderer::new);

        ModelPredicateProviderRegistry.register(ModItems.ENVELOPE, new Identifier("sealed"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.hasNbt() && Objects.requireNonNull(itemStack.getNbt()).contains("sender")) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}
