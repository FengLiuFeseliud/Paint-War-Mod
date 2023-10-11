package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PaintDetectorBlock extends BaseBlock {
    public PaintDetectorBlock(Settings settings, String name) {
        super(settings, name);
    }

    public PaintDetectorBlock(Settings settings, DyeColor color, String name) {
        super(settings, color, name);
    }

    @Override
    public boolean canSprayBlock() {
        return true;
    }

    @Override
    public BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock) {
        return IColor.getColor(ModBlocks.COLOR_PAINT_DETECTOR_BLOCKS, color).getDefaultState();
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return this.getColor() != null;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return this.getColor().getId();
    }
}
