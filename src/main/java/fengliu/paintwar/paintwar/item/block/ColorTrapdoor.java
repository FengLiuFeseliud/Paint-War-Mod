package fengliu.paintwar.paintwar.item.block;

import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ColorTrapdoor extends NoColorTrapdoor{
    public <B extends Block & IModBlock> ColorTrapdoor(B block) {
        super(block);
    }

    @Override
    public String getTextureName() {
        return ModBlockItem.NO_COLOR_TRAPDOOR.getTextureName();
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
