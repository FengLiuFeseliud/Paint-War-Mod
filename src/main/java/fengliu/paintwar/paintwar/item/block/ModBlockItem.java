package fengliu.paintwar.paintwar.item.block;

import fengliu.paintwar.paintwar.block.ColorDoorBlock;
import fengliu.paintwar.paintwar.block.ColorPaintDetectorBlock;
import fengliu.paintwar.paintwar.block.ModBlocks;
import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.item.BaseBlockItem;
import fengliu.paintwar.paintwar.util.item.BaseTallBlockItem;
import fengliu.paintwar.paintwar.util.item.IModItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

import java.util.List;

import static fengliu.paintwar.paintwar.item.ModItemGroups.BLOCK_GROUP;

public class ModBlockItem {
    public static final BaseBlockItem GRID_BRIDGE = register(new BaseBlockItem(ModBlocks.GRID_BRIDGE_BLOCK), BLOCK_GROUP);
    public static final List<ColorGridBridge> GRID_BRIDGES = RegisterUtil.registerBlockItems(ModBlocks.COLOR_GRID_BRIDGE_BLOCKS, ColorGridBridge::new, BLOCK_GROUP);
    public static final BaseBlockItem PAINT_DETECTOR = register(new BaseBlockItem(ModBlocks.PAINT_DETECTOR_BLOCK), BLOCK_GROUP);
    public static final List<ColorPaintDetector> COLOR_PAINT_DETECTORS = RegisterUtil.registerBlockItems(ModBlocks.COLOR_PAINT_DETECTOR_BLOCKS, ColorPaintDetector::new, BLOCK_GROUP);
    public static final NoColorDoor NO_COLOR_DOOR = register(new NoColorDoor(ModBlocks.NO_COLOR_DOOR_BLOCK), BLOCK_GROUP);
    public static final List<ColorDoor> COLOR_DOORS = RegisterUtil.registerBlockItems(ModBlocks.COLOR_DOOR_BLOCKS, ColorDoor::new, BLOCK_GROUP);

    public static <BI extends BlockItem & IModItem> BI register(BI blockItem, ItemGroup group){
        return RegisterUtil.registerBlockItem(blockItem, group);
    }

    public static void registerAllBlockItem(){

    }
}
