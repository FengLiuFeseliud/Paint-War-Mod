package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.block.BaseBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.DyeColor;

import java.util.List;

public class ModBlocks {
    public static final BaseBlock GRID_BRIDGE = register(new GridBridgeBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), "grid_bridge"));
    public static final List<BaseBlock> GRID_BRIDGES = RegisterUtil.registerColorBlocks(DyeColor.values(), dyeColor -> new ColorGridBridgeBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), dyeColor, "color_grid_bridge"));

    @Environment(EnvType.CLIENT)
    public static void setAllBlockRenderLayerMap(){
        BlockRenderLayerMap.INSTANCE.putBlock(GRID_BRIDGE, RenderLayer.getTranslucent());
        GRID_BRIDGES.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
    };

    public static BaseBlock register(BaseBlock block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
