package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.RegisterUtil;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;

import java.util.List;

public class ModBlocks {
    public static final GridBridgeBlock GRID_BRIDGE = register(new GridBridgeBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), "grid_bridge"));
    public static final List<ColorGridBridgeBlock> GRID_BRIDGES = RegisterUtil.registerColorBlocks(dyeColor -> new ColorGridBridgeBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), dyeColor, "color_grid_bridge"));

    @Environment(EnvType.CLIENT)
    public static void setAllBlockRenderLayerMap(){
        BlockRenderLayerMap.INSTANCE.putBlock(GRID_BRIDGE, RenderLayer.getTranslucent());
        GRID_BRIDGES.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
    };

    public static <B extends Block & IModBlock> B register(B block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
