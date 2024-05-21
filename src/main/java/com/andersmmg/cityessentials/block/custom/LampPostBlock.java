package com.andersmmg.cityessentials.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class LampPostBlock extends Block {
    public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");
    public static final BooleanProperty POWERED = Properties.POWERED;
    private static final VoxelShape VOXEL_SHAPE = Stream.of(
            Block.createCuboidShape(3, 0, 3, 13, 2, 13),
            Block.createCuboidShape(6, 13, 6, 10, 16, 10),
            Block.createCuboidShape(4, 2, 4, 12, 13, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    private static final VoxelShape VOXEL_SHAPE_CONNECTED = Block.createCuboidShape(6, 0, 6, 10, 16, 10);

    public LampPostBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CONNECTED, false).with(POWERED, false));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(CONNECTED)) {
            return VOXEL_SHAPE_CONNECTED;
        } else {
            return VOXEL_SHAPE;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONNECTED, POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState belowState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (belowState.isOf(this)) {
            return this.getDefaultState().with(CONNECTED, true);
        } else {
            return this.getDefaultState();
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return state.get(POWERED) ? 15 : 0;
        }
        return 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return state.get(POWERED) ? 15 : 0;
        }
        return 0;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) != world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_ALL);
        }
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        world.scheduleBlockTick(pos, this, 4);
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }
}
