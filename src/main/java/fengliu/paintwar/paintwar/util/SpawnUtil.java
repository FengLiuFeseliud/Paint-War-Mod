package fengliu.paintwar.paintwar.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 生成实体工具
 */
public class SpawnUtil {

    /**
     * 生成掉落物
     * @param itemEntity 物品实体
     * @param world 世界
     */
    public static void spawnItem(ItemEntity itemEntity, World world){
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
    }

    /**
     * 生成掉落物至玩家
     * @param stack 生成物品格
     * @param player 玩家
     * @param world 世界
     */
    public static void spawnItemToPlayer(ItemStack stack, PlayerEntity player, World world){
        Vec3d pos = player.getPos();
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        SpawnUtil.spawnItem(itemEntity, world);
    }

    /**
     * 生成掉落物至坐标
     * @param stack 生成物品格
     * @param pos 坐标
     * @param world 世界
     */
    public static void spawnItemToPos(ItemStack stack, BlockPos pos, World world){
        if (stack.isEmpty()){
            return;
        }

        ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, stack);
        SpawnUtil.spawnItem(itemEntity, world);
    }
}
