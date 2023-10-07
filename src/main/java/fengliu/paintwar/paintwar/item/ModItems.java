package fengliu.paintwar.paintwar.item;

import fengliu.paintwar.paintwar.item.tool.*;
import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;

import java.util.List;

import static fengliu.paintwar.paintwar.item.ModItemGroups.TOOL_GROUP;

public class ModItems {
    public static final BaseItem EMPTY_COLOR_PICKER = register(new EmptyColorPicker(new FabricItemSettings().maxCount(1), "empty_color_picker"), TOOL_GROUP);
    public static final List<BaseItem> COLOR_PICKERS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new ColorPicker(new FabricItemSettings().maxCount(1).maxDamage(64), dyeColor,"color_picker"), TOOL_GROUP);
    public static final BaseItem EMPTY_SPRAY_GUN = register(new BaseItem(new FabricItemSettings().maxCount(1), "empty_spray_gun"), TOOL_GROUP);
    public static final List<BaseItem> SPRAY_GUNS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new SprayGun(new FabricItemSettings().maxCount(1).maxDamage(64), dyeColor,"spray_gun"), TOOL_GROUP);
    public static final BaseItem EMPTY_BRUSH = register(new BaseItem(new FabricItemSettings().maxCount(1), "empty_brush"), TOOL_GROUP);
    public static final List<BaseItem> BRUSHS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new Brush(new FabricItemSettings().maxCount(1).maxDamage(128), dyeColor,"brush"), TOOL_GROUP);
    public static final BaseItem EMPTY_WATER_BALLOON = register(new EmptyWaterBalloon(new FabricItemSettings().maxCount(64), "empty_water_balloon"), TOOL_GROUP);
    public static final BaseItem WATER_BALLOON = register(new WaterBalloon(new FabricItemSettings().maxCount(64), "water_balloon"), TOOL_GROUP);
    public static final List<BaseItem> COLOR_WATER_BALLOONS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new ColorWaterBalloon(new FabricItemSettings().maxCount(64), dyeColor,"color_water_balloon"), TOOL_GROUP);
    public static final BaseItem EMPTY_WALL_GUN = register(new EmptyWallGun(new FabricItemSettings().maxCount(1), "empty_wall_gun"), TOOL_GROUP);
    public static final List<BaseItem> WALL_GUNS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new WallGun(new FabricItemSettings().maxCount(1).maxDamage(9), dyeColor,"wall_gun"), TOOL_GROUP);
    public static final List<BaseItem> WALL_SHELLS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new WallShell(new FabricItemSettings().maxCount(64), dyeColor,"wall_shell"), TOOL_GROUP);
    public static final BaseItem SCATTER_COLOR_GUN = register(new ScatterColorGun(new FabricItemSettings().maxCount(1), "scatter_color_gun"), TOOL_GROUP);
    public static final List<BaseItem> COLOR_SCATTER_COLOR_GUNS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new ColorScatterColorGun(new FabricItemSettings().maxCount(1), dyeColor,"color_scatter_color_gun"), TOOL_GROUP);
    public static final List<BaseItem> PAINT_SMOKE_BOMBS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new PaintSmokeBomb(new FabricItemSettings().maxCount(8), dyeColor,"paint_smoke_bomb"), TOOL_GROUP);
    public static final BaseItem SIGNAL_GUN = register(new SignalGun(new FabricItemSettings().maxCount(1), "signal_gun"), TOOL_GROUP);
    public static final List<BaseItem> SIGNAL_SHELLS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new SignalShell(new FabricItemSettings().maxCount(16), dyeColor,"signal_shell"), TOOL_GROUP);

    public static <BI extends BaseItem> BI register(BI item, ItemGroup group){
        return RegisterUtil.registerItem(item.name, item, group, RegisterUtil.Model.GENERATED);
    }

    public static BaseItem register(String id, ItemGroup group){
        return RegisterUtil.registerItem(id, new BaseItem(id), group, RegisterUtil.Model.GENERATED);
    }

    public static void registerAllItem(){

    }
}
