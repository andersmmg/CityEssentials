package com.andersmmg.cityessentials.block.custom;

import com.andersmmg.cityessentials.util.VoxelUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ExitSignBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty CEILING = BooleanProperty.of("ceiling");
    private static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(1, 4, 15, 15, 11, 16);
    private static final VoxelShape VOXEL_SHAPE_CEILING = Block.createCuboidShape(1, 9, 7.5, 15, 16, 8.5);

    public ExitSignBlock(Settings settings) {
        super(settings.luminance((state) -> 10));
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(CEILING, false));
    }

    protected static Direction getDirection(BlockState state) {
        return state.get(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(CEILING)) {
            return VoxelUtils.rotateShape(getDirection(state), VOXEL_SHAPE_CEILING);
        } else {
            return VoxelUtils.rotateShape(getDirection(state), VOXEL_SHAPE);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CEILING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction side = ctx.getSide();
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
        if (side == Direction.UP) {
            return null;
        } else if (side == Direction.DOWN) {
            return this.getDefaultState().with(FACING, direction).with(CEILING, true);
        } else {
            return this.getDefaultState().with(FACING, side);
        }
    }
}