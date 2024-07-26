package com.andersmmg.cityessentials.client.screen;

import com.andersmmg.cityessentials.CityEssentials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WalletScreen extends HandledScreen<WalletScreenHandler> {
    private static final Identifier TEXTURE = CityEssentials.id("textures/gui/container/wallet.png");

    public WalletScreen(WalletScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 133;
//        this.playerInventoryTitleY = this.backgroundHeight - 94;
        this.playerInventoryTitleY = 90000;
        this.titleY = 90000;
    }

    @Override
    protected void init() {
        super.init();
        Text text = getTitle();
        int textWidth = textRenderer.getWidth(text.getString());
        int yOffset = (this.height - this.backgroundHeight) / 2 + 6;
        addDrawableChild(new TextWidget(this.width / 2 - textWidth / 2, yOffset, textWidth, textRenderer.fontHeight, text, textRenderer));
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

}