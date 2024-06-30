package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.block.custom.MailDroppableBlock;
import com.andersmmg.cityessentials.block.entity.MailDroppable;
import com.andersmmg.cityessentials.client.screen.EnvelopeScreenHandler;
import com.andersmmg.cityessentials.sounds.ModSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnvelopeItem extends Item implements MailboxQuickAddable {
    public EnvelopeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound(user, user.getBlockPos(), ModSounds.ENVELOPE_OPEN, SoundCategory.PLAYERS, 1.0F, 1.0F);
        ItemStack stack = user.getStackInHand(hand);
        if (stack.hasNbt() && stack.getNbt().contains("sender")) {
            if (!world.isClient()) {
                CityEssentials.LOGGER.info("Opened Envelope: " + stack.getNbt().getString("sender"));
                // give player all items in the envelope
                for (ItemStack item : new EnvelopeInventory(stack).getItems()) {
                    user.getInventory().offerOrDrop(item);
                }
                ItemStack emptyStack = new ItemStack(this.asItem());
                return TypedActionResult.success(emptyStack);
            }
        } else {
            user.openHandledScreen(createScreenHandlerFactory(stack));
        }
        return TypedActionResult.success(stack);
    }

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new EnvelopeScreenHandler(syncId, inventory, new EnvelopeInventory(stack)), stack.getName());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) {
            return ActionResult.SUCCESS;
        }
        if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof MailDroppableBlock) {
            MailDroppable mailbox = (MailDroppable) context.getWorld().getBlockEntity(context.getBlockPos());
            assert mailbox != null;
            if (!mailbox.isInventoryFull()) {
                mailbox.addItem(context.getStack());
                context.getPlayer().getInventory().removeStack(context.getPlayer().getInventory().selectedSlot);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if (stack.hasNbt()) {
            NbtCompound nbtCompound = stack.getNbt();
            String string = nbtCompound.getString("sender");
            if (!StringHelper.isEmpty(string)) {
                tooltip.add(Text.translatable("container.envelope.tooltip.fromSender", string).formatted(Formatting.GRAY));
            }
            String string2 = nbtCompound.getString("receiver");
            if (!StringHelper.isEmpty(string2)) {
                tooltip.add(Text.translatable("container.envelope.tooltip.toReceiver", string2).formatted(Formatting.GRAY));
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        return tag.get("sender") != null || tag.get("receiver") != null;
    }
}
