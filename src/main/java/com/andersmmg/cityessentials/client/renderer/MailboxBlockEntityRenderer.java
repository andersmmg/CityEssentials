package com.andersmmg.cityessentials.client.renderer;

import com.andersmmg.cityessentials.block.custom.MailboxBlock;
import com.andersmmg.cityessentials.block.entity.MailboxBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;

public class MailboxBlockEntityRenderer implements BlockEntityRenderer<MailboxBlockEntity> {
    private final TextRenderer textRenderer;

    private static final int RENDER_DISTANCE = MathHelper.square(32);

    public MailboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.textRenderer = ctx.getTextRenderer();
    }

    @Override
    public void render(MailboxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!shouldRender(entity.getPos())) {
            return;
        }
        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(getRotation(entity)));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
        matrices.translate(0.0f, -0.33f, -0.2f);
        float textScale = 0.010f;
        matrices.scale(textScale, textScale, textScale);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));

        Text signText = entity.getText();
//        Text signText = Text.literal("Hello, World!");

        float f = (float) (-this.textRenderer.getWidth(signText) / 2);
        float g = (float) (-this.textRenderer.fontHeight / 2);

        this.textRenderer.draw(signText, f, g, 0, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 5);
        matrices.pop();
    }

    static boolean shouldRender(BlockPos pos) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson() && clientPlayerEntity.isUsingSpyglass()) {
            return true;
        } else {
            Entity entity = minecraftClient.getCameraEntity();
            return entity != null && entity.squaredDistanceTo(Vec3d.ofCenter(pos)) < (double) RENDER_DISTANCE;
        }

    }

    public int getRotation(MailboxBlockEntity entity) {
        BlockState state = entity.getCachedState();
        Direction dir = state.get(MailboxBlock.FACING);
        return switch (dir) {
            case NORTH -> 0;
            case SOUTH -> 180;
            case EAST -> 270;
            case WEST -> 90;
            default -> 0;
        };
    }
}