package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.entity.thrown.WaterBalloonEntity;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WaterBalloon extends BaseItem {

    public WaterBalloon(Settings settings, String name) {
        super(settings, name);
    }

    public WaterBalloon(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
    }

    public ThrownItemEntity getEntity(World world, PlayerEntity user){
        return new WaterBalloonEntity(user, world);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            ThrownItemEntity waterBalloon = this.getEntity(world, user);
            waterBalloon.setItem(itemStack);
            waterBalloon.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(waterBalloon);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
