package fengliu.paintwar.paintwar.item.block;

import fengliu.paintwar.paintwar.block.ModBlocks;
import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import fengliu.paintwar.paintwar.util.item.IModItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

import java.util.List;

import static fengliu.paintwar.paintwar.item.ModItemGroups.BLOCK_GROUP;

public class ModBlockItem {
    public static final BaseBlockItem GRID_BRIDGE = register(new BaseBlockItem(ModBlocks.GRID_BRIDGE), BLOCK_GROUP);
    public static final List<ColorGridBridge> GRID_BRIDGES = RegisterUtil.registerBlockItems(ModBlocks.GRID_BRIDGES, ColorGridBridge::new, BLOCK_GROUP);

    public static <BI extends BlockItem & IModItem> BI register(BI blockItem, ItemGroup group){
        return RegisterUtil.registerBlockItem(blockItem, group);
    }

    public static void registerAllBlockItem(){

    }
}
