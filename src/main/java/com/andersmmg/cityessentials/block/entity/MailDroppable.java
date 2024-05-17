package com.andersmmg.cityessentials.block.entity;

import net.minecraft.item.ItemStack;

public interface MailDroppable {
    public default boolean isInventoryFull() {
        return false;
    }

    public default void addItem(ItemStack itemStack) {
    }
}
