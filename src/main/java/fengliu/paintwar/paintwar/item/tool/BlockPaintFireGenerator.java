package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.block.ColorFireBlock;
import fengliu.paintwar.paintwar.block.ModBlocks;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.sound.ModSoundEvents;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPaintFireGenerator extends BaseItem {
    public BlockPaintFireGenerator(Settings settings, String name) {
        super(settings, name);
    }

    public static BlockState getFire(BlockPos pos, World world){
        BlockState useBlock = world.getBlockState(pos);
        for (ColorFireBlock fire: ModBlocks.COLOR_FIRE_BLOCKS){
            if (!fire.canPlaceAt(useBlock, world, pos)){
                continue;
            }
            return fire.getDefaultState();
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockPos posSide = pos.offset(context.getSide());

        BlockState downState = world.getBlockState(posSide);
        if (!downState.isAir() || world.getBlockState(pos).getBlock() instanceof ColorFireBlock){
            return super.useOnBlock(context);
        }

        world.playSound(context.getPlayer(), pos, ModSoundEvents.ITEM_USE_BLOCK_PAINT_FIRE_GENERATOR, SoundCategory.BLOCKS, 0.6F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        world.setBlockState(posSide, BlockPaintFireGenerator.getFire(pos, world));
        if (context.getWorld().isClient()){
            return super.useOnBlock(context);
        }

        context.getStack().damage(1, context.getPlayer(), player -> player.sendToolBreakStatus(context.getHand()));
        Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) context.getPlayer(), posSide, context.getStack());
        return super.useOnBlock(context);
    }
}
