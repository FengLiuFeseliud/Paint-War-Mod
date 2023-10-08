package fengliu.paintwar.paintwar.data.generation;

import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

import java.util.Optional;

import static net.minecraft.data.client.Models.GENERATED;

public class ModelsDataGeneration extends FabricModelProvider {
    public ModelsDataGeneration(FabricDataOutput output) {
        super(output);
    }

    /**
     * 生成块模型与块状态
     * <p>
     * 通过调用 {@link BaseBlock#generateBlockStateModel(BlockStateModelGenerator) generateBlockStateModel} 生成块状态
     * <p>
     * 通过调用 {@link BaseBlock#generateBlockModel(BlockStateModelGenerator) generateBlockModel} 生成块模型
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        RegisterUtil.BLOCKS.forEach(block -> {
            if (!(block instanceof BaseBlock baseBlock)){
                return;
            }

            baseBlock.generateBlockStateModel(blockStateModelGenerator);
            baseBlock.generateBlockModel(blockStateModelGenerator);
        });
    }

    /**
     * 生成物品模型
     * <p>
     * {@link BaseItem} 通过调用 {@link BaseItem#generateModel(ItemModelGenerator) generateModel} 生成 自定义/默认 generated 模型
     * <p>
     * 块物品模型在 {@link RegisterUtil#registerBlockItem(BlockItem, ItemGroup, RegisterUtil.Model) registerBlockItem} 注册后, 生成默认 parent 模型指向块模型
     */
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

            if (model == RegisterUtil.Model.PARENT){
                if (!(item instanceof BaseBlockItem baseItem)){
                    return;
                }
                BaseBlock block = (BaseBlock) baseItem.getBlock();
                itemModelGenerator.register(item, new Model(Optional.of(block.getModelId()), Optional.empty()));
            }
        });

        RegisterUtil.ITEM_MODEL.clear();
    }
}
