package com.andersmmg.cityessentials.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CashItem extends Item {
    private final int value;

    public CashItem(Settings settings, int value) {
        super(settings);
        this.value = value;
    }

    public static int getValue(ItemStack stack) {
        return stack.getItem() instanceof CashItem ? ((CashItem) stack.getItem()).value : 0;
    }

    public int getValue() {
        return value;
    }
}