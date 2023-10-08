package fengliu.paintwar.paintwar.util.item;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * 默认自动生成 "item/" 下的 "parent" 指向块模型, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateItemModels(ItemModelGenerator) generateItemModels}
 */
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

    /**
     * 生成物品配方 参考 {@link fengliu.paintwar.paintwar.data.generation.RecipeGenerator#generate(Consumer) generateRecipe}
     * @param exporter 配方生成器
     */
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

