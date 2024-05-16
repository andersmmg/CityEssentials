package com.andersmmg.cityessentials.client.screen;

import com.andersmmg.cityessentials.block.entity.SpeedLimitSignBlockEntity;
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
public class SpeedLimitEditScreen extends BaseOwoScreen<FlowLayout> {
    private final SpeedLimitSignBlockEntity signEntity;

    public SpeedLimitEditScreen(SpeedLimitSignBlockEntity entity) {
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
        textBox.setMaxLength(2);
        textBox.keyPress().subscribe((keyCode, scanCode, modifiers) -> {
            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                signEntity.setText(Text.literal(textBox.getText()));
                close();
            }
            return false;
        });

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.label(Text.translatable("gui.cityessentials.speed_limit.set")))
                        .child(textBox)
                        .gap(5)
                        .child(Components.button(Text.literal("Set"), button -> {
                            signEntity.setText(Text.literal(textBox.getText()));
                            close();
                        }).horizontalSizing(Sizing.fixed(68)))
                        .padding(Insets.of(10))
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }

}