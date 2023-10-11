package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ColorPaintDetectorBlock extends PaintDetectorBlock implements IColor {
    public ColorPaintDetectorBlock(Settings settings, DyeColor color, String name) {
        super(settings, color, name);
    }

    @Override
    public Identifier getModelId() {
        return ModBlocks.PAINT_DETECTOR_BLOCK.getModelId();
    }

    @Override
    public BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock) {
        return blockState;
    }
}
