package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.client.screen.BagScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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
}