package fengliu.paintwar.paintwar.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShapeUtil {

    public interface SetBlock{
        void set(BlockPos pos);
    }

    private static BlockPos nextRhombusRow(BlockPos pos, Direction nextDirection, boolean wallSpaceIn){
        if (!wallSpaceIn){
            return pos.offset(nextDirection);
        }
        return pos.down();
    }

    /**
     * 填充菱形行, 从行中心点向左右两边填充
     */
    private static void fillRhombusRow(BlockPos pos, int index, Direction ldirection, Direction rdirection, SetBlock setBlock){
        BlockPos lpos = pos, rpos = pos;

        for(int set_index = 1; set_index <= (index - 1) / 2; set_index++){
            lpos = lpos.offset(ldirection);
            setBlock.set(lpos);

            rpos = rpos.offset(rdirection);
            setBlock.set(rpos);
        }
    }

    /**
     * 填充菱形
     * @param size 菱形最大长度
     * @param pos 菱形中心间
     * @param world 世界
     * @param setBlock 方块填充回调
     */
    public static void rhombus(int size, BlockPos pos, World world, SetBlock setBlock){
        ShapeUtil.rhombus(size, pos, null, world, setBlock);
    }

    /**
     * 填充菱形
     * 填充方案, 计算出最高一行中心点, 从上置下填充每一行中心点, 并从中心点向左右两边填充
     *     ↓
     *   ← ↓ →
     * ← ← ↓ → →
     *   ← ↓ →
     *     √
     * @param size 菱形最大长度
     * @param pos 菱形中心间
     * @param direction 方向
     * @param world 世界
     * @param setBlock 方块填充回调
     */
    public static void rhombus(int size, BlockPos pos, @Nullable Direction direction, World world, SetBlock setBlock){
        Direction[] sprayDirections;
        if (direction == Direction.EAST){
            sprayDirections = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH};
        } else if (direction == Direction.WEST){
            sprayDirections = new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};
        } else {
            sprayDirections = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        }

        int soffset = Math.round((float) size / 2);
        boolean wallSpaceIn = false;

        BlockPos spos =  new BlockPos(pos.getX(), pos.getY(), pos.getZ()).down();
        for(int s_index = 0; s_index < soffset; s_index++){
            if (!world.getBlockState(spos.offset(sprayDirections[0])).isAir()){
                spos = spos.offset(sprayDirections[0]);
            } else {
                // 如果靠墙
                spos = spos.up();
                wallSpaceIn = true;
            }
        }

        // 填充菱形上半与中间
        for (int index = 1; index <= size; index = index + 2){
            setBlock.set(spos);
            if (index == 1){
                spos = ShapeUtil.nextRhombusRow(spos, sprayDirections[1], wallSpaceIn);
                continue;
            }

            fillRhombusRow(spos, index, sprayDirections[2], sprayDirections[3], setBlock);
            spos = ShapeUtil.nextRhombusRow(spos, sprayDirections[1], wallSpaceIn);
        }

        // 填充菱形下半
        for (int index = size - 2; index >= 0; index = index - 2){
            setBlock.set(spos);
            if (index == 1){
                break;
            }

            fillRhombusRow(spos, index, sprayDirections[2], sprayDirections[3], setBlock);
            spos = ShapeUtil.nextRhombusRow(spos, sprayDirections[1], wallSpaceIn);

        }
    }

    public static void quadrilateral(int widthSize,int heightSize, BlockPos pos, @Nullable Direction direction, SetBlock setBlock){
        Direction[] directions;
        if (direction == Direction.EAST || direction == Direction.WEST){
            directions = new Direction[]{Direction.NORTH, Direction.SOUTH};
        } else {
            directions = new Direction[]{Direction.WEST, Direction.EAST};
        }

        BlockPos spos = pos.offset(directions[0], Math.round((float) widthSize / 2));
        for(int index = 0; index < heightSize; index++){
            for (int row_index = 0; row_index < widthSize; row_index++){
                setBlock.set(spos);
                spos = spos.offset(directions[1]);
            }
            spos = spos.offset(directions[0], widthSize).up();
        }
    }

    public static void fallQuadrilateral(int widthSize,int heightSize, BlockPos pos, @Nullable Direction direction, SetBlock setBlock){
        Direction[] directions;
        if (direction == Direction.EAST){
            directions = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH};
        } else if (direction == Direction.WEST){
            directions = new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};
        } else {
            directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        }

        int heightOffset = Math.round((float) heightSize / 2);
        int widthOffset = Math.round((float) widthSize / 2);

        BlockPos spos =  pos.offset(directions[0], heightOffset).offset(directions[2], widthOffset);
        for(int index = 0; index < heightSize; index++){
            for (int row_index = 0; row_index < widthSize; row_index++){
                setBlock.set(spos);
                spos = spos.offset(directions[3]);
            }
            spos = spos.offset(directions[2], widthSize).offset(directions[1]);
        }
    }
}
