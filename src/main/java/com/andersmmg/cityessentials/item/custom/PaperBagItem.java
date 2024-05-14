package com.andersmmg.cityessentials.item.custom;

import com.andersmmg.cityessentials.sounds.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PaperBagItem extends BagItem {
    public PaperBagItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound(user, user.getBlockPos(), ModSounds.PAPER_BAG_OPEN, SoundCategory.PLAYERS, 1.0F, 1.0F);
        return super.use(world, user, hand);
    }
}