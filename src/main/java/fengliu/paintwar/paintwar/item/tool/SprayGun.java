package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.sound.ModSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class SprayGun extends ColorPicker {
    public final DyeColor dyeColor;

    public SprayGun(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
    }

    @Override
    public Item getEmptyItem() {
        return ModItems.EMPTY_SPRAY_GUN;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().west().north();
        Map<BlockState, BlockState> sprayBlocks = new HashMap<>();

        for(int index = 1; index < 10; index++){
            BlockState oldBlock = world.getBlockState(pos);
            if (!sprayBlocks.containsKey(oldBlock)){
                sprayBlocks.put(oldBlock, Brush.sprayBlock(world, context.getBlockPos(), world.getBlockState(pos), this.getColor()));
            }

            Brush.spray(world, pos, sprayBlocks.get(oldBlock));
            pos = pos.east();
            if (index % 3 == 0){
                pos = pos.west(3).south();
            }
        }

        world.playSound(context.getPlayer(), pos, ModSoundEvents.ITEM_USE_SPRAY_GUN, SoundCategory.BLOCKS, 0.6F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        if (context.getWorld().isClient()){
            return ActionResult.SUCCESS;
        }

        context.getStack().damage(1, context.getPlayer(), player -> {
            player.setStackInHand(context.getHand(), ModItems.EMPTY_SPRAY_GUN.getDefaultStack());
            world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, SoundCategory.PLAYERS);
        });
        Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) context.getPlayer(), pos, context.getStack());
        return ActionResult.SUCCESS;
    }
}
