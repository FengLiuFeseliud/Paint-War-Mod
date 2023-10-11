package fengliu.paintwar.paintwar.util;

import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import fengliu.paintwar.paintwar.util.item.IModItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RegisterUtil {
    /**
     * 统计被注册的物品列表, 用于数据生成, 数据生成参考 {@link fengliu.paintwar.paintwar.data.generation.LangGeneration#generateTranslations(FabricLanguageProvider.TranslationBuilder) generateTranslations} {@link fengliu.paintwar.paintwar.data.generation.RecipeGenerator#generate(Consumer) generateRecipe}
     */
    public static final List<IModItem> ITEMS = new ArrayList<>();
    /**
     * 统计被注册的方块列表, 用于数据生成, 数据生成参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateBlockStateModels(BlockStateModelGenerator) generateBlockStateModels}
     */
    public static final List<IModBlock> BLOCKS = new ArrayList<>();


    /**
     * 注册物品
     */
    public static <I extends Item> I registerItem(Identifier id, I item, ItemGroup group){
        ITEMS.add((IModItem) item);
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(item.getDefaultStack()));
        return Registry.register(Registries.ITEM, id, item);
    }

    /**
     * 注册物品
     */
    public static <I extends Item> I registerItem(String id, I item, ItemGroup group){
        return RegisterUtil.registerItem(IdUtil.get(id), item, group);
    }

    /**
     * 注册方块物品
     */
    public static <BI extends BlockItem & IModItem> BI registerBlockItem(BI item, ItemGroup group){
        return RegisterUtil.registerItem(IdUtil.get(((IModBlock) item.getBlock()).getBlockName()), item, group);
    }

    public interface registerItem<BI extends BlockItem & IModItem>{
        <B extends Block & IModBlock> BI get(B block);
    }

    /**
     * 注册多个方块物品, {@link registerItem#get(Block)} ) register} 传入当前正在注册方块, 返回 {@link Item} 完成方块物品创建
     */
    public static <B extends Block & IModBlock, BI extends BlockItem & IModItem> List<BI> registerBlockItems(List<B> blocks, registerItem<BI> register, ItemGroup group){
        List<BI> blockItems = new ArrayList<>();
        blocks.forEach(block -> {
            BI blockItem = register.get(block);
            if (blockItem.getBlock() instanceof IColor color){
                try {
                    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> color.getColor().getMapColor().color, blockItem);
                } catch (RuntimeException ignored) {

                }
            }

            blockItems.add(RegisterUtil.registerItem(IdUtil.get(((IModBlock) blockItem.getBlock()).getBlockName()), blockItem, group));
        });
        return blockItems;
    }


    public interface colorItem<I extends Item & IModItem & IColor>{
        I get(DyeColor dyeColor);
    }

    /**
     * 注册 16色 颜色物品, {@link colorItem#get(DyeColor) colorItem} 传入当前正在注册物品颜色, 返回 {@link BaseItem} 完成方块创建
     */
    public static <I extends Item & IModItem & IColor> List<I> registerColorItems(colorItem<I> colorItem, @Nullable ItemGroup group){
        List<I> items = new ArrayList<>();
        for (DyeColor dyeColor: DyeColor.values()){
            I item = colorItem.get(dyeColor);
            try {
                ColorProviderRegistry.ITEM.register((stack, tintIndex) -> dyeColor.getMapColor().color, item);
            } catch (RuntimeException ignored){

            }

            if (group == null){
                continue;
            }

            items.add(RegisterUtil.registerItem(item.getItemName(), item, group));
        }
        return items;
    }

    /**
     * 注册方块
     */
    public static <B extends Block & IModBlock> B registerBlock(B block) {
        BLOCKS.add(block);
        return Registry.register(Registries.BLOCK, IdUtil.get(block.getBlockName()), block);
    }

    public interface colorBlock<B extends Block & IModBlock>{
        B get(DyeColor dyeColor);
    }

    /**
     * 注册 16色 颜色方块, {@link colorItem#get(DyeColor) colorItem} 传入当前正在注册方块颜色, 返回 {@link Block} 完成方块创建
     */
    public static <B extends Block & IModBlock> List<B> registerColorBlocks(colorBlock<B> colorBlock){
        List<B> blocks = new ArrayList<>();
        for (DyeColor dyeColor: DyeColor.values()){
            B block = colorBlock.get(dyeColor);
            if (!(block instanceof IColor)){
                continue;
            }

            try {
                ColorProviderRegistry.ITEM.BLOCK.register((state, world, pos, tintIndex) -> dyeColor.getFireworkColor(), block);
            } catch (RuntimeException ignored){

            }

            blocks.add(RegisterUtil.registerBlock(block));
        }
        return blocks;
    }

}
