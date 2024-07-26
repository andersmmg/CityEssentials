package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.client.screen.WalletScreenHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WalletItem extends Item {
    public final SoundEvent openSound;

    public WalletItem(Settings settings, SoundEvent openSound) {
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
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new WalletScreenHandler(syncId, inventory, new WalletInventory(stack)), stack.getName());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        int total = getTotalValue(stack);
        if (total > 0) {
            tooltip.add(Text.translatable("container.wallet.value", total).formatted(Formatting.DARK_GREEN));
        }
    }

    public int getTotalValue(ItemStack stack) {
        WalletInventory inventory = new WalletInventory(stack);
        int total = 0;
        for (ItemStack itemStack : inventory.getItems()) {
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() instanceof CashItem) {
                total += ((CashItem) itemStack.getItem()).getValue() * itemStack.getCount();
            }
        }
        return total;
    }
}