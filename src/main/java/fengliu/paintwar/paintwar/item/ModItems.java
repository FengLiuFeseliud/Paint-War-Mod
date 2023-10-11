package fengliu.paintwar.paintwar.item;

import fengliu.paintwar.paintwar.item.tool.*;
import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.List;

import static fengliu.paintwar.paintwar.item.ModItemGroups.TOOL_GROUP;

public class ModItems {
    public static final EmptyColorPicker EMPTY_COLOR_PICKER = register(new EmptyColorPicker(new FabricItemSettings().maxCount(1), "empty_color_picker"), TOOL_GROUP);
    public static final List<ColorPicker> COLOR_PICKERS = RegisterUtil.registerColorItems(dyeColor -> new ColorPicker(new FabricItemSettings().maxCount(1).maxDamage(64), dyeColor,"color_picker"), TOOL_GROUP);
    public static final BaseItem EMPTY_SPRAY_GUN = register(new BaseItem(new FabricItemSettings().maxCount(1), "empty_spray_gun"), TOOL_GROUP);
    public static final List<SprayGun> SPRAY_GUNS = RegisterUtil.registerColorItems(dyeColor -> new SprayGun(new FabricItemSettings().maxCount(1).maxDamage(64), dyeColor,"spray_gun"), TOOL_GROUP);
    public static final Item EMPTY_BRUSH = register(new BaseItem(new FabricItemSettings().maxCount(1), "empty_brush"), TOOL_GROUP);
    public static final List<Brush> BRUSHS = RegisterUtil.registerColorItems(dyeColor -> new Brush(new FabricItemSettings().maxCount(1).maxDamage(128), dyeColor,"brush"), TOOL_GROUP);
    public static final EmptyWaterBalloon EMPTY_WATER_BALLOON = register(new EmptyWaterBalloon(new FabricItemSettings().maxCount(64), "empty_water_balloon"), TOOL_GROUP);
    public static final WaterBalloon WATER_BALLOON = register(new WaterBalloon(new FabricItemSettings().maxCount(64), "water_balloon"), TOOL_GROUP);
    public static final List<ColorWaterBalloon> COLOR_WATER_BALLOONS = RegisterUtil.registerColorItems(dyeColor -> new ColorWaterBalloon(new FabricItemSettings().maxCount(64), dyeColor,"color_water_balloon"), TOOL_GROUP);
    public static final EmptyWallGun EMPTY_WALL_GUN = register(new EmptyWallGun(new FabricItemSettings().maxCount(1), "empty_wall_gun"), TOOL_GROUP);
    public static final List<WallGun> WALL_GUNS = RegisterUtil.registerColorItems(dyeColor -> new WallGun(new FabricItemSettings().maxCount(1).maxDamage(9), dyeColor,"wall_gun"), TOOL_GROUP);
    public static final List<WallShell> WALL_SHELLS = RegisterUtil.registerColorItems(dyeColor -> new WallShell(new FabricItemSettings().maxCount(64), dyeColor,"wall_shell"), TOOL_GROUP);
    public static final ScatterColorGun SCATTER_COLOR_GUN = register(new ScatterColorGun(new FabricItemSettings().maxCount(1), "scatter_color_gun"), TOOL_GROUP);
    public static final List<ColorScatterColorGun> COLOR_SCATTER_COLOR_GUNS = RegisterUtil.registerColorItems(dyeColor -> new ColorScatterColorGun(new FabricItemSettings().maxCount(1), dyeColor,"color_scatter_color_gun"), TOOL_GROUP);
    public static final List<PaintSmokeBomb> PAINT_SMOKE_BOMBS = RegisterUtil.registerColorItems(dyeColor -> new PaintSmokeBomb(new FabricItemSettings().maxCount(8), dyeColor,"paint_smoke_bomb"), TOOL_GROUP);
    public static final SignalGun SIGNAL_GUN = register(new SignalGun(new FabricItemSettings().maxCount(1), "signal_gun"), TOOL_GROUP);
    public static final List<SignalShell> SIGNAL_SHELLS = RegisterUtil.registerColorItems(dyeColor -> new SignalShell(new FabricItemSettings().maxCount(16), dyeColor,"signal_shell"), TOOL_GROUP);

    public static <BI extends BaseItem> BI register(BI item, ItemGroup group){
        return RegisterUtil.registerItem(item.name, item, group);
    }

    public static BaseItem register(String id, ItemGroup group){
        return RegisterUtil.registerItem(id, new BaseItem(id), group);
    }

    public static void registerAllItem(){

    }
}
