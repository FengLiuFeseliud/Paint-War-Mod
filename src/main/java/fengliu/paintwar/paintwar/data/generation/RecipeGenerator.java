package fengliu.paintwar.paintwar.data.generation;

import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        RegisterUtil.ITEMS.forEach(item -> {
            try {
                if (item instanceof BaseItem baseItem){
                    baseItem.generateRecipe(exporter);
                } else if (item instanceof BaseBlockItem blockItem){
                    blockItem.generateRecipe(exporter);
                }
            } catch (IllegalStateException | ConcurrentModificationException ignored){

            }
        });
    }
}
