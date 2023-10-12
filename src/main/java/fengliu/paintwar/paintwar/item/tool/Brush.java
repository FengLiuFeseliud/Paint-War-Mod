package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Brush extends ColorPicker {
    public final DyeColor dyeColor;

    public Brush(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
    }

    public Item getEmptyItem() {
        return ModItems.EMPTY_BRUSH;
    }

    /**
     * 获取修改颜色后的方块
     * @param world 世界
     * @param pos 方块坐标
     * @param blockState 修改方块
     * @param color 需修改为的颜色
     * @return 修改颜色后的方块
     */
    public static BlockState sprayBlock(World world, @Nullable BlockPos pos, BlockState blockState, @Nullable DyeColor color) {
        if (color == null){
            return blockState;
        }

        if (blockState.getBlock() instanceof IModBlock block){
            if (!block.canSprayBlock()){
                return block.onSprayBlock(world, pos, blockState, color, false);
            }

            BlockState state = block.onSprayBlock(world, pos, blockState, color, true);
            if (!blockState.isOf(state.getBlock())){
                return state;
            }
        }

        String blockPath = Registries.BLOCK.getId(blockState.getBlock()).getPath();
        for (DyeColor oldColor : DyeColor.values()) {
            if (!blockPath.startsWith(oldColor.getName())) {
                continue;
            }

            return Registries.BLOCK.get(
                    new Identifier(
                        Registries.BLOCK.getId(blockState.getBlock()).getNamespace(),
                        blockPath.replace(oldColor.getName(), color.getName())
                    )
            ).getDefaultState();
        }
        return blockState;
    }

    /**
     * 修改方块
     * @param world 世界
     * @param pos 方块坐标
     * @param sprayBlock 目标修改为方块
     */
    public static void spray(World world, BlockPos pos, BlockState sprayBlock){
        if (sprayBlock.isAir()){
            return;
        }

        if (!(sprayBlock.getBlock() instanceof DoorBlock)){
            world.setBlockState(pos, sprayBlock);
            return;
        }

        // 修改门颜色时不产生更新, 有更新门就修改不了了
        BlockState doorState = world.getBlockState(pos);
        world.setBlockState(pos, sprayBlock.getBlock().getStateWithProperties(doorState), 3, 0);
        if (doorState.get(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER)){
            world.setBlockState(pos.up(), sprayBlock.getBlock().getStateWithProperties(world.getBlockState(pos.up())), 3, 0);
        } else {
            world.setBlockState(pos.down(), sprayBlock.getBlock().getStateWithProperties(world.getBlockState(pos.down())), 3, 0);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Brush.spray(context.getWorld(), context.getBlockPos(),  Brush.sprayBlock(context.getWorld(), context.getBlockPos(), context.getWorld().getBlockState(context.getBlockPos()), this.getColor()));
        if (context.getWorld().isClient()) {
            return ActionResult.SUCCESS;
        }

        if (context.getStack().damage(1, context.getWorld().random, (ServerPlayerEntity) context.getPlayer())) {
            context.getPlayer().setStackInHand(context.getHand(), ModItems.EMPTY_BRUSH.getDefaultStack());
        }
        return ActionResult.SUCCESS;
    }
}
