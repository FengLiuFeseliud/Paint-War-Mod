package fengliu.paintwar.paintwar.item.block;

import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseTallBlockItem;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.DyeColor;

import java.util.function.Consumer;

public class ColorDoor extends NoColorDoor {
    public <B extends Block & IModBlock> ColorDoor(B block) {
        super(block);
    }

    @Override
    public String getTextureName() {
        return ModBlockItem.NO_COLOR_DOOR.getTextureName();
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, this, 1)
                .input(ModBlockItem.NO_COLOR_DOOR)
                .input(DyeItem.byColor(((IColor) this.getBlock()).getColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }
}
