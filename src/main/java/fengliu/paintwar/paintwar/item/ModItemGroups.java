package fengliu.paintwar.paintwar.item;


import fengliu.paintwar.paintwar.util.IdUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ItemGroup TOOL_GROUP = FabricItemGroup.builder(IdUtil.get("tool_group"))
            .icon(() -> new ItemStack(ModItems.EMPTY_BRUSH))
            .build();
}
