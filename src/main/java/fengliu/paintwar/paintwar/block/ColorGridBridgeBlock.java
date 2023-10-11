package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.item.tool.Brush;
import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ColorGridBridgeBlock extends GridBridgeBlock implements IColor {

    public ColorGridBridgeBlock(Settings settings, DyeColor color, String name) {
        super(settings, color, name);
    }

    @Override
    public Identifier getModelId() {
        return IdUtil.get(this.getPrefixedPath() + ((IModBlock) ModBlocks.GRID_BRIDGE).getTextureName());
    }

    @Override
    public boolean canSprayBlock() {
        return false;
    }

    @Override
    public BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock) {
        pos = pos.down();
        BlockState wallBlockState = world.getBlockState(pos);

        int count = 0;
        while (wallBlockState.isAir()){
            if (count > 50){
                return blockState;
            }
            pos = pos.down();
            wallBlockState = world.getBlockState(pos);
            count ++;
        }

        if (wallBlockState.isOf(Blocks.VOID_AIR)){
            return blockState;
        }

        world.setBlockState(pos, Brush.sprayBlock(world, pos, wallBlockState, color));
        return blockState;
    }
}
