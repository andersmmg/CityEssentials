package com.andersmmg.cityessentials.client.screen;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.record.EnvelopeSealPacket;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class EnvelopeScreen extends HandledScreen<EnvelopeScreenHandler> {
    private static final Identifier TEXTURE = CityEssentials.id("textures/gui/container/envelope.png");

    public EnvelopeScreen(EnvelopeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 133;
//        this.playerInventoryTitleY = this.backgroundHeight - 94;
        this.playerInventoryTitleY = 90000;
    }

    @Override
    protected void init() {
        super.init();
        OwoUIAdapter<FlowLayout> adapter = OwoUIAdapter.create(this, Containers::verticalFlow);
        this.addDrawableChild(adapter);
        buildOverlay(adapter.rootComponent);
        adapter.inflateAndMount();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    protected void buildOverlay(FlowLayout rootComponent) {
        rootComponent
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        var textBox = Components.textBox(Sizing.fixed(100), "");
        textBox.keyPress().subscribe((keyCode, scanCode, modifiers) -> {
            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                CityEssentials.ENVELOPE_SEAL_CHANNEL.clientHandle().send(new EnvelopeSealPacket(textBox.getText(), Hand.MAIN_HAND));
                close();
            }
            // Override keypress so you can type E
            return true;
        });

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Containers.horizontalFlow(Sizing.content(), Sizing.content())
                                .child(Components.label(Text.translatable("gui.cityessentials.envelope.label")))
                                .child(textBox)
                                .gap(5)
                                .child(Components.button(Text.translatable("gui.cityessentials.envelope.seal"), button -> {
                                    if (!textBox.getText().isEmpty()) {
                                        CityEssentials.ENVELOPE_SEAL_CHANNEL.clientHandle().send(new EnvelopeSealPacket(textBox.getText(), Hand.MAIN_HAND));
                                        close();
                                    } else {
                                        CityEssentials.LOGGER.info("Text is empty");
                                    }
                                }).horizontalSizing(Sizing.fixed(68)))
                                .padding(Insets.of(10))
                                .verticalAlignment(VerticalAlignment.CENTER)
                                .horizontalAlignment(HorizontalAlignment.CENTER)
                        )
                        .padding(Insets.of(backgroundHeight + 30, 0, 0, 0))
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }

}
