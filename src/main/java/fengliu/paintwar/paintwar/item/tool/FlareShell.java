package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.DyeColor;

import java.util.function.Consumer;

public class FlareShell extends BaseItem implements IColor {
    private final DyeColor color;

    public FlareShell(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.color = dyeColor;
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, this, 8)
                .pattern("IGI")
                .pattern("ICI")
                .input('I', Items.IRON_INGOT)
                .input('G', Items.GUNPOWDER)
                .input('C', DyeItem.byColor(this.getColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }
}
