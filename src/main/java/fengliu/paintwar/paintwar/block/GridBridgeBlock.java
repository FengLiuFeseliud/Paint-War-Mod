package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.block.FacingBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GridBridgeBlock extends FacingBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);

    public GridBridgeBlock(Settings settings, String name) {
        super(settings, name);
    }

    public GridBridgeBlock(Settings settings, DyeColor color, String name) {
        super(settings, color, name);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ColorGridBridgeBlock.SHAPE;
    }

    @Override
    public boolean canSprayBlock() {
        return true;
    }

    @Override
    public BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock) {
        return IColor.getColor(ModBlocks.COLOR_GRID_BRIDGE_BLOCKS, color).getDefaultState();
    }
}
