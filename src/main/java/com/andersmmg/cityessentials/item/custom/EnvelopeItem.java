package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.block.custom.MailboxBlock;
import com.andersmmg.cityessentials.block.entity.MailboxBlockEntity;
import com.andersmmg.cityessentials.client.screen.EnvelopeScreenHandler;
import com.andersmmg.cityessentials.sounds.ModSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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
        user.openHandledScreen(createScreenHandlerFactory(stack));
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
        if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof MailboxBlock) {
            MailboxBlockEntity mailbox = (MailboxBlockEntity) context.getWorld().getBlockEntity(context.getBlockPos());
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
        EnvelopeInventory envelopeInventory = new EnvelopeInventory(stack);
        for (ItemStack itemStack : envelopeInventory.getItems()) {
            if (!itemStack.isEmpty()) {
                tooltip.add(Text.translatable("container.envelope.tooltip.filled").formatted(Formatting.GOLD));
                return;
            }
        }
        tooltip.add(Text.translatable("container.envelope.tooltip.empty").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
