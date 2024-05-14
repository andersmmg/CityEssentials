package com.andersmmg.cityessentials.block.entity;

import com.andersmmg.cityessentials.block.custom.CashRegisterBlock;
import com.andersmmg.cityessentials.client.screen.Mod3X3ContainerScreenHandler;
import com.andersmmg.cityessentials.sounds.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class CashRegisterBlockEntity extends LootableContainerBlockEntity {
    private final ViewerCountManager stateManager = new ViewerCountManager() {

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            CashRegisterBlockEntity.this.playSound(state, ModSounds.CASH_REGISTER_OPEN);
            CashRegisterBlockEntity.this.setOpen(state, true);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            CashRegisterBlockEntity.this.playSound(state, ModSounds.CASH_REGISTER_CLOSE);
            CashRegisterBlockEntity.this.setOpen(state, false);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof Mod3X3ContainerScreenHandler) {
                Inventory inventory = ((Mod3X3ContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == CashRegisterBlockEntity.this;
            }
            return false;
        }
    };
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(size(), ItemStack.EMPTY);

    public CashRegisterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CASH_REGISTER_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return items;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.items = list;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        if (this.stateManager.getViewerCount() > 0) {
            return null;
        } else {
            return new Mod3X3ContainerScreenHandler(syncId, playerInventory, this);
        }
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(CashRegisterBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(CashRegisterBlock.FACING).getVector();
        double d = (double) this.pos.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double e = (double) this.pos.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double f = (double) this.pos.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public int size() {
        return 3 * 3;
    }
}