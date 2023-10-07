package fengliu.paintwar.paintwar.util;

import fengliu.paintwar.paintwar.PaintWar;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IdUtil {

    public static Identifier get(String name){
        return new Identifier(PaintWar.MOD_ID, name);
    }

    public static Identifier getTooltip(String name){
        return new Identifier(PaintWar.MOD_ID, name);
    }

    public static String getItemInfo(String name){
        return PaintWar.MOD_ID + ".item." + name + ".info";
    }

    public static String getItemInfo(String name, int index){
        return IdUtil.getItemInfo(name) + "." + index;
    }

    public static String getItemTooltip(String name){
        return "item." + PaintWar.MOD_ID + "." + name + ".tooltip";
    }

    public static String getItemTooltip(String name, int index){
        return "item." + PaintWar.MOD_ID + "." + name + ".tooltip." + index;
    }

    public static String getBlockItemTooltip(String name){
        return "block.item." + PaintWar.MOD_ID + "." + name + ".tooltip";
    }

    public static String getBlockItemTooltip(String name, int index){
        return "block.item." + PaintWar.MOD_ID + "." + name + ".tooltip." + index;
    }

    public static Text getItemName(String name){
        return Text.translatable("item." + PaintWar.MOD_ID + "." + name);
    }

    public static String getDisplayName(String name){
        return "block." + PaintWar.MOD_ID + "." + name + ".display.name";
    }

    public static String getDeathMessage(String name){
        return PaintWar.MOD_ID + ".death." + name;
    }
}
