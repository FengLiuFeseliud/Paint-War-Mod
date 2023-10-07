package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.entity.thrown.PaintSmokeBombEntity;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.stat.Stats;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class PaintSmokeBomb extends BaseItem implements IColor {
    public static final int COOL_PAINT_SMOKE_BOMB_TIME = 40;
    private final DyeColor color;

    public PaintSmokeBomb(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.color = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this, 4)
                .pattern(" SS")
                .pattern("IGI")
                .pattern("ICI")
                .input('S', ItemTags.SLABS)
                .input('I', Items.IRON_INGOT)
                .input('G', Items.GUNPOWDER)
                .input('C', DyeItem.byColor(this.getColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            ThrownItemEntity paintSmokeBombEntity = new PaintSmokeBombEntity(user, world);
            paintSmokeBombEntity.setItem(itemStack);
            paintSmokeBombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(paintSmokeBombEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        for (Item item: ModItems.PAINT_SMOKE_BOMBS){
            user.getItemCooldownManager().set(item, COOL_PAINT_SMOKE_BOMB_TIME);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
