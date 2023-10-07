package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EmptyWaterBalloon extends BaseItem {
    public EmptyWaterBalloon(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.getBlockState(raycast(world, user, RaycastContext.FluidHandling.WATER).getBlockPos()).isOf(Blocks.WATER)){
            return super.use(world, user, hand);
        }

        user.getStackInHand(hand).decrement(1);
        if (!user.getInventory().insertStack(ModItems.WATER_BALLOON.getDefaultStack())){
            user.dropStack(ModItems.WATER_BALLOON.getDefaultStack());
            return super.use(world, user, hand);
        }
        return super.use(world, user, hand);
    }
}
