package com.andersmmg.cityessentials.client.screen;

import com.andersmmg.cityessentials.block.entity.MailboxBlockEntity;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MailboxEditScreen extends BaseOwoScreen<FlowLayout> {
    private final MailboxBlockEntity signEntity;

    public MailboxEditScreen(MailboxBlockEntity entity) {
        this.signEntity = entity;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        var textBox = Components.textBox(Sizing.fixed(68), signEntity.getText().getString());
        textBox.setMaxLength(13);
        textBox.keyPress().subscribe((keyCode, scanCode, modifiers) -> {
            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                signEntity.setText(Text.literal(textBox.getText()));
                close();
            }
            return false;
        });

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.label(Text.translatable("gui.cityessentials.mailbox.edit_name")))
                        .child(textBox)
                        .gap(5)
                        .child(Components.button(Text.literal("Save"), button -> {
                            signEntity.setText(Text.literal(textBox.getText()));
                            close();
                        }).horizontalSizing(Sizing.fixed(68)))
                        .padding(Insets.of(10))
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }

}