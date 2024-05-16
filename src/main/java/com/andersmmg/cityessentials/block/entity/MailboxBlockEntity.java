package com.andersmmg.cityessentials.block.entity;

import com.andersmmg.cityessentials.block.custom.MailboxBlock;
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

public class MailboxBlockEntity extends LootableContainerBlockEntity {
    private final ViewerCountManager stateManager = new ViewerCountManager() {

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            MailboxBlockEntity.this.playSound(state, ModSounds.MAILBOX_OPEN);
            MailboxBlockEntity.this.setOpen(state, true);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            MailboxBlockEntity.this.playSound(state, ModSounds.MAILBOX_CLOSE);
            MailboxBlockEntity.this.setOpen(state, false);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof Mod3X3ContainerScreenHandler) {
                Inventory inventory = ((Mod3X3ContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == MailboxBlockEntity.this;
            }
            return false;
        }
    };
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(size(), ItemStack.EMPTY);

    public MailboxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.MAILBOX_BLOCK_ENTITY, blockPos, blockState);
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

    public boolean isInventoryFull() {
        // Get the mailbox inventory
        DefaultedList<ItemStack> inventory = this.getInventory();

        // Check if any slot in the inventory is empty
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) {
                return false; // Inventory is not full
            }
        }

        return true; // Inventory is full
    }

    public void addItem(ItemStack itemStack) {
        // Get the mailbox inventory
        DefaultedList<ItemStack> inventory = this.getInventory();

        // Find the first empty slot in the inventory and add the item
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()) {
                inventory.set(i, itemStack.copy()); // Add the item to the empty slot
                this.setFlag(this.getCachedState(), true); // Set the flag up
                return; // Item added successfully
            }
        }
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
        return new Mod3X3ContainerScreenHandler(syncId, playerInventory, this);
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
            // Check if there are items inside
            boolean hasItems = this.hasItemsInAnySlot();

            // Set the flag based on whether there are items
            setFlag(this.getCachedState(), hasItems);
        }
    }

    private boolean hasItemsInAnySlot() {
        for (ItemStack itemStack : this.items) {
            if (!itemStack.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(MailboxBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    void setFlag(BlockState state, boolean flag) {
        this.world.setBlockState(this.getPos(), state.with(MailboxBlock.FLAG, flag), Block.NOTIFY_ALL);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(MailboxBlock.FACING).getVector();
        double d = (double) this.pos.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double e = (double) this.pos.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double f = (double) this.pos.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public int size() {
        return 3 * 3;
    }

    public DefaultedList<ItemStack> getInventory() {
        return items;
    }
}