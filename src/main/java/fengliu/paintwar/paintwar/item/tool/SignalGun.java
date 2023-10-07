package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.entity.thrown.SignalShellEntity;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SignalGun extends BaseItem {

    public static final int COOL_SIGNAL_GUN_TIME = 20;

    public SignalGun(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return super.use(world, user, hand);
        }

        for (int index = 0; index < PlayerInventory.MAIN_SIZE + 9; index++){
            ItemStack stack = user.getInventory().getStack(index);
            if (!(stack.getItem() instanceof SignalShell)){
                continue;
            }

            ThrownItemEntity signalShellEntity = new SignalShellEntity(user, world);
            signalShellEntity.setItem(stack);
            signalShellEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(signalShellEntity);

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            user.getItemCooldownManager().set(this, COOL_SIGNAL_GUN_TIME);
            break;
        }
        return super.use(world, user, hand);
    }
}
