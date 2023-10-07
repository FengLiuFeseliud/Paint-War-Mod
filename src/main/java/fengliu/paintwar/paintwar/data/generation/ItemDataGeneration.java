package fengliu.paintwar.paintwar.data.generation;

import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

import static net.minecraft.data.client.Models.GENERATED;

public class ItemDataGeneration extends FabricModelProvider {
    public ItemDataGeneration(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        RegisterUtil.ITEM_MODEL.forEach((item, model) -> {
            if (model == null){
                return;
            }

            if (model == RegisterUtil.Model.GENERATED){
                if (item instanceof BaseItem baseItem){
                    baseItem.generateModel(itemModelGenerator);
                    return;
                }
                itemModelGenerator.register(item, GENERATED);
            }
        });

        RegisterUtil.ITEM_MODEL.clear();
    }
}
