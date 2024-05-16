package com.andersmmg.cityessentials.block.entity;

import com.andersmmg.cityessentials.CityEssentials;
import com.andersmmg.cityessentials.record.SignUpdatePacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public interface EditableSign {
    // Method to send update packet to clients
    default void sendUpdatePacket(BlockPos pos, Text text) {
        CityEssentials.SIGN_UPDATE_CHANNEL.clientHandle().send(new SignUpdatePacket(pos, text));
    }

    default void setText(Text text) {
    }

    default Text getText() {
        return Text.literal("");
    }
}
