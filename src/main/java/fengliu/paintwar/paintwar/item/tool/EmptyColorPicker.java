package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.sound.ModSoundEvents;
import fengliu.paintwar.paintwar.util.SpawnUtil;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
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

            ItemStack oldStack = player.getStackInHand(hand);
            ItemStack stack = item.getDefaultStack();
            EnchantmentHelper.fromNbt(oldStack.getEnchantments()).forEach(stack::addEnchantment);

            player.setStackInHand(hand, stack);
            break;
        }
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        super.generateModel(itemModelGenerator);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (this instanceof SprayGun || this instanceof Brush){
            return super.useOnEntity(stack, user, entity, hand);
        }

        if (!user.getWorld().isClient()){
            Criteria.USING_ITEM.trigger((ServerPlayerEntity) user, user.getStackInHand(hand));
        }

        EmptyColorPicker.pickerColorInHand(
                entity.getLootTable().getPath(),
                user,
                hand
        );
        user.getWorld().playSound(user, entity.getBlockPos(), ModSoundEvents.ITEM_USE_COLOR_PICKER, SoundCategory.PLAYERS, 1F, user.getWorld().getRandom().nextFloat() * 0.4F + 0.8F);
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (this instanceof SprayGun || this instanceof Brush){
            return super.use(world, user, hand);
        }

        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.WATER);
        if (hitResult.getType() != HitResult.Type.BLOCK){
            return super.use(world, user, hand);
        }

        if (!user.getWorld().isClient()){
            Criteria.USING_ITEM.trigger((ServerPlayerEntity) user, user.getStackInHand(hand));
        }

        EmptyColorPicker.pickerColorInHand(
                Registries.BLOCK.getId(world.getBlockState(hitResult.getBlockPos()).getBlock()).getPath(),
                user,
                hand
        );
        user.getWorld().playSound(user, hitResult.getBlockPos(), ModSoundEvents.ITEM_USE_COLOR_PICKER, SoundCategory.PLAYERS, 1F, user.getWorld().getRandom().nextFloat() * 0.4F + 0.8F);
        return super.use(world, user, hand);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        String itemPath = Registries.ITEM.getId(slot.getStack().getItem()).getPath();
        for (Item item : ModItems.COLOR_PICKERS) {
            if(!itemPath.contains(((IColor) item).getColor().getName())){
                continue;
            }

            if (!player.getWorld().isClient()){
                Criteria.USING_ITEM.trigger((ServerPlayerEntity) player, stack);
            }

            ItemStack colorPickerItem = item.getDefaultStack();
            colorPickerItem.setDamage(stack.getDamage());

            player.currentScreenHandler.setCursorStack(colorPickerItem);
            player.getWorld().playSound(player, player.getBlockPos(), ModSoundEvents.ITEM_USE_COLOR_PICKER, SoundCategory.PLAYERS, 1F, player.getWorld().getRandom().nextFloat() * 0.4F + 0.8F);
            return true;
        }
        return false;
    }
}
