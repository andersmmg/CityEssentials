package com.andersmmg.cityessentials.client.renderer;

import com.andersmmg.cityessentials.block.custom.SpeedLimitSignBlock;
import com.andersmmg.cityessentials.block.entity.SpeedLimitSignBlockEntity;
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
import net.minecraft.util.Colors;
import net.minecraft.util.math.*;

public class SpeedLimitSignBlockEntityRenderer implements BlockEntityRenderer<SpeedLimitSignBlockEntity> {
    private final TextRenderer textRenderer;

    private static final int RENDER_DISTANCE = MathHelper.square(64);

    public SpeedLimitSignBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.textRenderer = ctx.getTextRenderer();
    }

    @Override
    public void render(SpeedLimitSignBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!shouldRender(entity.getPos())) {
            return;
        }
        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(getRotation(entity)));
        matrices.translate(0.0f, 0.0f, -0.11f);
        float textScale = 0.019f;
        float speedTextScale = 2.2f;
        matrices.scale(textScale, textScale, textScale);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));

        Text firstText = Text.literal("SPEED");
        Text secondText = Text.literal("LIMIT");
        Text signText = entity.getText();

        float f = (float) (-this.textRenderer.getWidth(signText) / 2);
        float g = (float) (-this.textRenderer.fontHeight / 2);

        this.textRenderer.draw(firstText, ((float) -this.textRenderer.getWidth(firstText) / 2) + 0.5f, g - 14, Colors.BLACK, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 255);

        this.textRenderer.draw(secondText, ((float) -this.textRenderer.getWidth(secondText) / 2) + 0.5f, g - 4, Colors.BLACK, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 255);

        matrices.scale(speedTextScale, speedTextScale, 1);
        this.textRenderer.draw(signText, f + 0.5f, g + 6, Colors.BLACK, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 255);
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

    public int getRotation(SpeedLimitSignBlockEntity entity) {
        BlockState state = entity.getCachedState();
        Direction dir = state.get(SpeedLimitSignBlock.FACING);
        return switch (dir) {
            case NORTH -> 0;
            case SOUTH -> 180;
            case EAST -> 270;
            case WEST -> 90;
            default -> 0;
        };
    }
}