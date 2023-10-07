package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.entity.thrown.ColorWaterBalloonEntity;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ColorWaterBalloon extends WaterBalloon implements IColor {
    public final DyeColor dyeColor;

    public ColorWaterBalloon(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, this, 1)
                .input(ModItems.EMPTY_WATER_BALLOON)
                .input(DyeItem.byColor(this.getColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
    }

    @Override
    public ThrownItemEntity getEntity(World world, PlayerEntity user) {
        return new ColorWaterBalloonEntity(user, world);
    }
}
