package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.client.screen.BagScreenHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BagItem extends Item {
    public final SoundEvent openSound;

    public BagItem(Settings settings, SoundEvent openSound) {
        super(settings);
        this.openSound = openSound;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound(user, user.getBlockPos(), openSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        ItemStack stack = user.getStackInHand(hand);
        user.openHandledScreen(createScreenHandlerFactory(stack));
        return TypedActionResult.success(stack);
    }

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
            return new BagScreenHandler(syncId, inventory, new BagInventory(stack));
        }, stack.getName());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        BagInventory inventory = new BagInventory(stack);
        DefaultedList<ItemStack> items = DefaultedList.of();
        for (ItemStack itemStack : inventory.getItems()) {
            if (itemStack.isEmpty()) continue;
            boolean found = false;
            for (ItemStack item : items) {
                if (ItemStack.areItemsEqual(item, itemStack)) {
                    item.increment(itemStack.getCount());
                    found = true;
                    break;
                }
            }
            if (!found) {
                items.add(itemStack.copy());
            }
        }
        int i = 0;
        for (ItemStack itemStack : items) {
            ++i;
            if (i > 4) continue;
            MutableText mutableText = itemStack.getName().copy();
            mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
            tooltip.add(mutableText.formatted(Formatting.GRAY));
        }
        if (items.size() - i > 0) {
            tooltip.add(Text.translatable("container.box.more", items.size() - i).formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
        }
    }
}