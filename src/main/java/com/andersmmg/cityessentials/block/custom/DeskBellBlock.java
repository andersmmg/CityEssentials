package com.andersmmg.cityessentials.block.custom;

import com.andersmmg.cityessentials.sounds.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;

public class DeskBellBlock extends Block {
    public static final BooleanProperty PRESSED = BooleanProperty.of("pressed");
    private static final VoxelShape VOXEL_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(6, 0.5, 6, 10, 2, 10), Block.createCuboidShape(5, 0, 5, 11, 0.5, 11), BooleanBiFunction.OR);

    public DeskBellBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PRESSED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(PRESSED)) {
            return ActionResult.FAIL;
        } else {
            world.setBlockState(pos, state.cycle(PRESSED), Block.NOTIFY_ALL);
            world.scheduleBlockTick(pos, this, 10, TickPriority.NORMAL);

            world.playSound(null, pos, ModSounds.BELL_DING, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(PRESSED)) {
            world.setBlockState(pos, state.cycle(PRESSED), Block.NOTIFY_ALL);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PRESSED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction side = ctx.getSide();
        if (side != Direction.UP) {
            return null;
        }
        return this.getDefaultState().with(PRESSED, false);
    }
}
