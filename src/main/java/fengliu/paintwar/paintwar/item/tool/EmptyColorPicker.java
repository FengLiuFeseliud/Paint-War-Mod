package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.SpawnUtil;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EmptyColorPicker extends BaseItem {

    public EmptyColorPicker(Settings settings, String name) {
        super(settings, name);
    }

    public EmptyColorPicker(Settings settings, DyeColor dyeColor, String name) {
        super(settings, dyeColor, name);
    }

    public static void pickerColorInHand(String path, PlayerEntity player, Hand hand){
        for (Item item : ModItems.COLOR_PICKERS) {
            if(!path.contains(((IColor) item).getColor().getName())){
                continue;
            }

            ItemStack colorPickerItem = item.getDefaultStack();
            colorPickerItem.setDamage(player.getStackInHand(hand).getDamage());

            player.setStackInHand(hand, colorPickerItem);
            break;
        }
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        super.generateModel(itemModelGenerator);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient || this instanceof SprayGun || this instanceof Brush){
            return super.useOnEntity(stack, user, entity, hand);
        }

        EmptyColorPicker.pickerColorInHand(
                entity.getLootTable().getPath(),
                user,
                hand
        );
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || this instanceof SprayGun || this instanceof Brush){
            return super.use(world, user, hand);
        }

        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.WATER);
        if (hitResult.getType() != HitResult.Type.BLOCK){
            return super.use(world, user, hand);
        }


        EmptyColorPicker.pickerColorInHand(
                Registries.BLOCK.getId(world.getBlockState(hitResult.getBlockPos()).getBlock()).getPath(),
                user,
                hand
        );
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient || this instanceof SprayGun || this instanceof Brush){
            return super.postHit(stack, target, attacker);
        }

        String lootPath = target.getLootTable().getPath();
        for (Item item : ModItems.COLOR_PICKERS) {
            if(!lootPath.contains(((IColor) item).getColor().getName())){
                continue;
            }

            ItemStack colorPickerItem = item.getDefaultStack();
            colorPickerItem.setDamage(stack.getDamage());

            stack.decrement(1);
            SpawnUtil.spawnItem(new ItemEntity(attacker.world, attacker.getX(), attacker.getY(), attacker.getZ(), colorPickerItem), attacker.world);
            return true;
        }
        return false;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (ClickType.LEFT == clickType){
            return false;
        }

        String itemPath = Registries.ITEM.getId(slot.getStack().getItem()).getPath();
        for (Item item : ModItems.COLOR_PICKERS) {
            if(!itemPath.contains(((IColor) item).getColor().getName())){
                continue;
            }

            ItemStack colorPickerItem = item.getDefaultStack();
            colorPickerItem.setDamage(stack.getDamage());

            player.currentScreenHandler.setCursorStack(colorPickerItem);
            return true;
        }
        return false;
    }
}
