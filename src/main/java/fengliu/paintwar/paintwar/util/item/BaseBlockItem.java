package fengliu.paintwar.paintwar.util.item;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class BaseBlockItem extends BlockItem {
    private final String tooltipKey;

    public BaseBlockItem(BaseBlock block) {
        super(block, new FabricItemSettings().maxCount(64));
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, block.getBlockName());
    }

    public BaseBlockItem(BaseBlock block, Settings settings) {
        super(block, settings);
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, block.getBlockName());
    }

    public BaseBlockItem(Block block, String id) {
        super(block, new FabricItemSettings().maxCount(64));
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, id);
    }

    public BaseBlockItem(Block block, String id, Settings settings) {
        super(block, settings);
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, id);
    }

    public void generateRecipe(Consumer<RecipeJsonProvider> exporter){

    }

    private static String getTooltipKey(Block block, String id){
        if (block instanceof IColor color){
            return IdUtil.getBlockItemTooltip(color.getTextureName());
        }
        return IdUtil.getBlockItemTooltip(id);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}

