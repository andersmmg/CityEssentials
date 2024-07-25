package com.andersmmg.cityessentials.block.entity;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class OpenClosedSignBlockEntity extends BlockEntity implements EditableSign {
    private Text text = Text.literal("");

    public OpenClosedSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OPEN_CLOSED_SIGN_BLOCK_ENTITY, blockPos, blockState);
    }

    // Method to update the text
    public void setText(Text text) {
        this.text = text;
        if (world.isClient) {
            sendUpdatePacket(this.pos, this.text); // Send packet to server
        } else {
            markDirty();
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    // Getter for the text
    public Text getText() {
        return this.text;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("text", Text.Serializer.toJson(this.text));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.text = Text.Serializer.fromJson(nbt.getString("text"));
    }

    public void tick() {
        // unused
    }

}