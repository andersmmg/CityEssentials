package com.andersmmg.cityessentials.item.custom;

import io.wispforest.owo.util.ImplementedInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

final class EnvelopeInventory implements ImplementedInventory {
    private final ItemStack stack;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

    EnvelopeInventory(ItemStack stack) {
        this.stack = stack;
        NbtCompound tag = stack.getSubNbt("Items");

        if (tag != null) {
            Inventories.readNbt(tag, items);
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty() {
        NbtCompound tag = stack.getOrCreateSubNbt("Items");
        Inventories.writeNbt(tag, items);
    }
}