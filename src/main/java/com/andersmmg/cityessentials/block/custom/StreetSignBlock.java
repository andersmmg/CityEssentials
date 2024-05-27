package com.andersmmg.cityessentials.block.custom;

import com.andersmmg.cityessentials.block.entity.StreetSignBlockEntity;
import com.andersmmg.cityessentials.client.screen.StreetSignEditScreen;
import com.andersmmg.cityessentials.item.ModItems;
import com.andersmmg.cityessentials.util.VoxelUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StreetSignBlock extends BlockWithEntity {
    public static final BooleanProperty TOP = BooleanProperty.of("top");
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape VOXEL_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(6.5, 0, 6.5, 9.5, 16, 9.5), Block.createCuboidShape(6, 11, 0, 10, 15, 16), BooleanBiFunction.OR);
    private static final VoxelShape VOXEL_SHAPE_TOP = Block.createCuboidShape(0, 0, 6, 16, 4, 10);

    public StreetSignBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TOP, false));
    }

    protected static Direction getDirection(BlockState state) {
        return state.get(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StreetSignBlockEntity) {
            if (player.getStackInHand(hand).isOf(ModItems.MARKER)) {
                if (world.isClient) {
                    showEditScreen((StreetSignBlockEntity) blockEntity);
                }
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    @Environment(EnvType.CLIENT)
    private void showEditScreen(StreetSignBlockEntity blockEntity) {
        if (blockEntity == null) {
            return;
        }
        StreetSignEditScreen screen = new StreetSignEditScreen(blockEntity);
        MinecraftClient.getInstance().setScreen(screen);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory) ((Object) blockEntity));
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StreetSignBlockEntity) {
            ((StreetSignBlockEntity) blockEntity).tick();
        }
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StreetSignBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TOP)) {
            return VoxelUtils.rotateShape(getDirection(state), VOXEL_SHAPE_TOP);
        }
        return VoxelUtils.rotateShape(getDirection(state), VOXEL_SHAPE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TOP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState belowState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (belowState.isOf(this)) {
            return this.getDefaultState().with(FACING, belowState.get(FACING)).with(TOP, true);
        } else {
            return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
    }
}