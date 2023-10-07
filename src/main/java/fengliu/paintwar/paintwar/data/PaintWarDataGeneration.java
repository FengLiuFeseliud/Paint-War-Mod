package fengliu.paintwar.paintwar.data;

import fengliu.paintwar.paintwar.data.generation.LangGeneration;
import fengliu.paintwar.paintwar.data.generation.ItemDataGeneration;
import fengliu.paintwar.paintwar.data.generation.RecipeGenerator;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PaintWarDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemDataGeneration::new);
        pack.addProvider(LangGeneration::new);
        pack.addProvider(RecipeGenerator::new);
    }

}
