package fengliu.paintwar.paintwar.util.item;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.Optional;
import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;

public interface IModItem {
    String PREFIXED_PATH = "item/";

    String getItemName();

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    String getTextureName();

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    default String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    /**
     * 生成物品模型
     * @param itemModelGenerator 物品模型生成器
     */
    default void generateModel(ItemModelGenerator itemModelGenerator){
        if (this instanceof BlockItem blockItem){
            itemModelGenerator.register((Item) this, new Model(Optional.of(((IModBlock) blockItem.getBlock()).getModelId()), Optional.empty()));
            return;
        }
        GENERATED.upload(ModelIds.getItemModelId((Item) this), TextureMap.layer0(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath())), itemModelGenerator.writer);
    }

    /**
     * 生成物品配方
     * @param exporter 配方生成器
     */
    default void generateRecipe(Consumer<RecipeJsonProvider> exporter){

    }
}
