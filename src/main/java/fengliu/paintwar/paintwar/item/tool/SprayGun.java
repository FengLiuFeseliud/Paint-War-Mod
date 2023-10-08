package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.item.ModItems;
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
        BlockPos pos = context.getBlockPos().west().north();
        Map<BlockState, BlockState> sprayBlocks = new HashMap<>();

        for(int index = 1; index < 10; index++){
            BlockState oldBlock = context.getWorld().getBlockState(pos);
            if (!sprayBlocks.containsKey(oldBlock)){
                sprayBlocks.put(oldBlock, Brush.sprayBlock(context.getWorld(), context.getBlockPos(), context.getWorld().getBlockState(pos), this.getColor()));
            }

            context.getWorld().setBlockState(pos, sprayBlocks.get(oldBlock));
            pos = pos.east();
            if (index % 3 == 0){
                pos = pos.west(3).south();
            }
        }

        if (context.getWorld().isClient()){
            return ActionResult.SUCCESS;
        }

        if (context.getStack().damage(1, context.getWorld().random, (ServerPlayerEntity) context.getPlayer())){
            PlayerEntity player = context.getPlayer();

            player.setStackInHand(context.getHand(), ModItems.EMPTY_SPRAY_GUN.getDefaultStack());
            context.getWorld().playSound(player, player.getBlockPos(), SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, SoundCategory.PLAYERS);
        }
        return ActionResult.SUCCESS;
    }
}
