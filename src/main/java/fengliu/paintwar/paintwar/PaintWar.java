package fengliu.paintwar.paintwar;

import fengliu.paintwar.paintwar.block.ModBlocks;
import fengliu.paintwar.paintwar.entity.ModEntitys;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.item.block.ModBlockItem;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaintWar implements ModInitializer {
    public static final String MOD_ID = "paintwar";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerAllItem();
        ModBlockItem.registerAllBlockItem();

        ModBlocks.registerAllBlock();
        ModEntitys.registerAllEntity();
    }
}
