package fengliu.paintwar.paintwar.util;

import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RegisterUtil {
    /**
     * 支持生成的物品模型
     */
    public enum Model{
        GENERATED,
        PARENT;
    }

    /**
     * 需要生成模型的物品表, 数据生成参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateItemModels(ItemModelGenerator) generateItemModels}
     */
    public static final Map<Item, Model> ITEM_MODEL = new HashMap<>();
    /**
     * 统计被注册的物品列表, 用于数据生成, 数据生成参考 {@link fengliu.paintwar.paintwar.data.generation.LangGeneration#generateTranslations(FabricLanguageProvider.TranslationBuilder) generateTranslations} {@link fengliu.paintwar.paintwar.data.generation.RecipeGenerator#generate(Consumer) generateRecipe}
     */
    public static final List<Item> ITEMS = new ArrayList<>();
    /**
     * 统计被注册的方块列表, 用于数据生成, 数据生成参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateBlockStateModels(BlockStateModelGenerator) generateBlockStateModels}
     */
    public static final List<Block> BLOCKS = new ArrayList<>();


    /**
     * 注册物品
     */
    public static <I extends Item> I registerItem(Identifier id, I item, ItemGroup group, Model model){
        ITEM_MODEL.put(item, model);
        ITEMS.add(item);
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(item.getDefaultStack()));
        return Registry.register(Registries.ITEM, id, item);
    }

    /**
     * 注册物品
     */
    public static <I extends Item> I registerItem(String id, I item, ItemGroup group, Model model){
        return RegisterUtil.registerItem(IdUtil.get(id), item, group, model);
    }

    /**
     * 注册方块物品
     */
    public static <BI extends BlockItem> BI registerBlockItem(BI item, ItemGroup group, Model model){
        return RegisterUtil.registerItem(IdUtil.get(((BaseBlock) item.getBlock()).getBlockName()), item, group, model);
    }

    public interface registerItem{
        BaseBlockItem get(BaseBlock block);
    }

    /**
     * 注册多个方块物品, {@link registerItem#get(BaseBlock) register} 传入当前正在注册方块, 返回 {@link BaseBlockItem} 完成方块物品创建
     */
    public static List<BaseBlockItem> registerBlockItems(List<BaseBlock> blocks, registerItem register, ItemGroup group){
        List<BaseBlockItem> baseBlockItems = new ArrayList<>();
        blocks.forEach(block -> {
            BaseBlockItem blockItem = register.get(block);
            if (block.getColor() != null){
                try {
                    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> block.getColor().getMapColor().color, blockItem);
                } catch (RuntimeException ignored){

                }
            }

            baseBlockItems.add(RegisterUtil.registerItem(IdUtil.get(((BaseBlock) blockItem.getBlock()).getBlockName()), blockItem, group, Model.PARENT));
        });
        return baseBlockItems;
    }


    public interface colorItem{
        BaseItem get(DyeColor dyeColor);
    }

    /**
     * 注册 16色 颜色物品, {@link colorItem#get(DyeColor) colorItem} 传入当前正在注册物品颜色, 返回 {@link BaseItem} 完成方块创建
     */
    public static List<BaseItem> registerColorItems(colorItem colorItem, @Nullable ItemGroup group){
        List<BaseItem> items = new ArrayList<>();
        for (DyeColor dyeColor: DyeColor.values()){
            BaseItem item = colorItem.get(dyeColor);
            try {
                ColorProviderRegistry.ITEM.register((stack, tintIndex) -> dyeColor.getMapColor().color, item);
            } catch (RuntimeException ignored){

            }

            if (group == null){
                continue;
            }

            items.add(RegisterUtil.registerItem(item.name, item, group, Model.GENERATED));
        }
        return items;
    }

    /**
     * 注册方块
     */
    public static BaseBlock registerBlock(BaseBlock block) {
        BLOCKS.add(block);
        return Registry.register(Registries.BLOCK, IdUtil.get(block.getBlockName()), block);
    }

    public interface colorBlock{
        BaseBlock get(DyeColor dyeColor);
    }

    /**
     * 注册 16色 颜色方块, {@link colorItem#get(DyeColor) colorItem} 传入当前正在注册方块颜色, 返回 {@link BaseBlock} 完成方块创建
     */
    public static List<BaseBlock> registerColorBlocks(colorBlock colorBlock){
        List<BaseBlock> blocks = new ArrayList<>();
        for (DyeColor dyeColor: DyeColor.values()){
            BaseBlock block = colorBlock.get(dyeColor);
            try {
                ColorProviderRegistry.ITEM.BLOCK.register((state, world, pos, tintIndex) -> dyeColor.getFireworkColor(), block);
            } catch (RuntimeException ignored){

            }

            blocks.add(RegisterUtil.registerBlock(block));
        }
        return blocks;
    }

}
