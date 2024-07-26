package com.andersmmg.cityessentials.client.screen;

import com.andersmmg.cityessentials.item.custom.CashItem;
import com.andersmmg.cityessentials.item.custom.WalletItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class WalletScreenHandler extends ScreenHandler {
    public static final int SLOT_COUNT = 4;
    private final Inventory inventory;

    public WalletScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4));
    }

    public WalletScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.WALLET_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 4);
        inventory.onOpen(playerInventory.player);

        int j;
        for (j = 0; j < SLOT_COUNT; ++j) {
            this.addSlot(new Slot(inventory, j, 53 + j * 18, 19) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.getItem() instanceof CashItem;
                }
            });
        }

        for (j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    @Override
    public void onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity) {
        if (slotId >= 0) { // slotId < 0 are used for networking internals
            ItemStack stack = getSlot(slotId).getStack();

            if (stack.getItem() instanceof WalletItem) {
                // Prevent moving wallets around
                return;
            }
        }

        super.onSlotClick(slotId, clickData, actionType, playerEntity);
    }
}