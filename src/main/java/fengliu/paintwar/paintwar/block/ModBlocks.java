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
    public static final GridBridgeBlock GRID_BRIDGE_BLOCK = register(new GridBridgeBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), "grid_bridge"));
    public static final List<ColorGridBridgeBlock> COLOR_GRID_BRIDGE_BLOCKS = RegisterUtil.registerColorBlocks(dyeColor -> new ColorGridBridgeBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), dyeColor, "color_grid_bridge"));
    public static final PaintDetectorBlock PAINT_DETECTOR_BLOCK = register(new PaintDetectorBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), "paint_detector"));
    public static final List<ColorPaintDetectorBlock> COLOR_PAINT_DETECTOR_BLOCKS = RegisterUtil.registerColorBlocks(dyeColor -> new ColorPaintDetectorBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque(), dyeColor, "color_paint_detector"));

    @Environment(EnvType.CLIENT)
    public static void setAllBlockRenderLayerMap(){
        BlockRenderLayerMap.INSTANCE.putBlock(GRID_BRIDGE_BLOCK, RenderLayer.getTranslucent());
        COLOR_GRID_BRIDGE_BLOCKS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
    };

    public static <B extends Block & IModBlock> B register(B block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
