package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.entity.thrown.WallShellEntity;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmptyWallGun extends BaseItem {
    public static final int COOL_WALL_GUN_TIME = 80;

    public EmptyWallGun(Settings settings, String name) {
        super(settings, name);
    }

    public EmptyWallGun(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
    }

    public DyeColor getColor(){
        return null;
    }

    public static void coolWallGun(PlayerEntity player){
        player.getItemCooldownManager().set(ModItems.EMPTY_WALL_GUN, COOL_WALL_GUN_TIME);
        for(Item item: ModItems.WALL_GUNS){
            player.getItemCooldownManager().set(item, COOL_WALL_GUN_TIME);
        }
    }

    public static List<Block> getWallBlocks(World world, PlayerEntity player, DyeColor blockColor){
        List<Block> blocks = new ArrayList<>();
        int needSize = WallShellEntity.HEIGHT_SIZE * WallShellEntity.WIDTH_SIZE;
        for (int index = 0; index < PlayerInventory.MAIN_SIZE + 9; index++) {
            ItemStack stack = player.getInventory().getStack(index);
            if (!(stack.getItem() instanceof BlockItem blockItem) || stack.isEmpty()){
                continue;
            }

            if(Arrays.stream(DyeColor.values()).noneMatch(
                    color -> Registries.BLOCK.getId(blockItem.getBlock()).getPath().startsWith(color.getName()))){
                continue;
            }

            for (int count = 0; count < stack.getCount(); count++){
                if (blocks.size() == needSize){
                    break;
                }
                blocks.add(Brush.sprayBlock(world, null, blockItem.getBlock().getDefaultState(), blockColor).getBlock());
                stack.decrement(1);
            }

            if (blocks.size() != needSize){
                continue;
            }
            break;
        }
        return blocks;
    }

    public WallShellEntity getShellEntity(World world, PlayerEntity player, List<Block> wallBlocks){
        return new WallShellEntity(player, world, wallBlocks);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return super.use(world, player, hand);
        }

        List<Block> wallBlocks = EmptyWallGun.getWallBlocks(world, player, this.getColor());
        if (wallBlocks.isEmpty()){
            return super.use(world, player, hand);
        }

        WallShellEntity wallShell = this.getShellEntity(world, player, wallBlocks);
        wallShell.setVelocity(player, player.getPitch(), player.getYaw(), 0F, 3F, 0F);
        world.spawnEntity(wallShell);

        EmptyWallGun.coolWallGun(player);
        if (player.getStackInHand(hand).damage(1, world.getRandom(), (ServerPlayerEntity) player)){
            player.setStackInHand(hand, ModItems.EMPTY_WALL_GUN.getDefaultStack());
        }
        return super.use(world, player, hand);
    }
}
