package fengliu.paintwar.paintwar.item.block;

import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.DyeItem;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ColorGridBridge extends BaseBlockItem {

    public <B extends Block & IModBlock> ColorGridBridge(B block) {
        super(block);
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, this, 1)
                .input(ModBlockItem.GRID_BRIDGE)
                .input(DyeItem.byColor(((BaseBlock) this.getBlock()).getColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }
}
