package fengliu.paintwar.paintwar.client;

import fengliu.paintwar.paintwar.block.ModBlocks;
import fengliu.paintwar.paintwar.entity.ModEntitys;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PaintWarClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.setAllBlockRenderLayerMap();
        ModEntitys.registerAllEntityRenderer();
    }
}
