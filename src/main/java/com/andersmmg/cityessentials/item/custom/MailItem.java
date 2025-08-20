package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.tags.ModTags;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import java.util.Objects;

public abstract class MailItem extends Item {
    public MailItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) {
            return ActionResult.FAIL;
        }
        if (context.getWorld().getBlockState(context.getBlockPos()).getRegistryEntry().isIn(ModTags.MAIL_DROPPABLE)) {
            if (context.getWorld().getBlockEntity(context.getBlockPos()) instanceof Inventory container) {
                PlayerInventory playerInventory = Objects.requireNonNull(context.getPlayer()).getInventory();
                ItemStack itemStack = playerInventory.getStack(playerInventory.selectedSlot).copy();
                ItemStack itemStack2 = HopperBlockEntity.transfer(playerInventory, container, playerInventory.removeStack(playerInventory.selectedSlot), null);

                if (itemStack2.isEmpty()) {
                    container.markDirty();

                    return ActionResult.SUCCESS;
                }
                playerInventory.setStack(playerInventory.selectedSlot, itemStack);
            }
            return ActionResult.FAIL;
        }
        return super.useOnBlock(context);
    }
}
