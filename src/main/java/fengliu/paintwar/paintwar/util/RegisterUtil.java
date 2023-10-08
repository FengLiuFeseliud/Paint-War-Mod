package fengliu.paintwar.paintwar.util;

import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
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

public class RegisterUtil {
    public enum Model{
        GENERATED,
        PARENT;
    }

    public static final Map<Item, Model> ITEM_MODEL = new HashMap<>();
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();


    public static <I extends Item> I registerItem(Identifier id, I item, ItemGroup group, Model model){
        ITEM_MODEL.put(item, model);
        ITEMS.add(item);
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(item.getDefaultStack()));
        return Registry.register(Registries.ITEM, id, item);
    }

    public static <I extends Item> I registerItem(String id, I item, ItemGroup group, Model model){
        return RegisterUtil.registerItem(IdUtil.get(id), item, group, model);
    }

    public static <BI extends BlockItem> BI registerBlockItem(BI item, ItemGroup group, Model model){
        return RegisterUtil.registerItem(IdUtil.get(((BaseBlock) item.getBlock()).getBlockName()), item, group, model);
    }

    public interface registerItem{
        BaseBlockItem get(BaseBlock block);
    }

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

    public static List<BaseItem> registerColorItems(DyeColor[] dyeColors, colorItem colorItem, @Nullable ItemGroup group){
        List<BaseItem> items = new ArrayList<>();
        for (DyeColor dyeColor: dyeColors){
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

    public static BaseBlock registerBlock(BaseBlock block) {
        BLOCKS.add(block);
        return Registry.register(Registries.BLOCK, IdUtil.get(block.getBlockName()), block);
    }

    public interface colorBlock{
        BaseBlock get(DyeColor dyeColor);
    }

    public static List<BaseBlock> registerColorBlocks(DyeColor[] dyeColors, colorBlock colorBlock){
        List<BaseBlock> blocks = new ArrayList<>();
        for (DyeColor dyeColor: dyeColors){
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
