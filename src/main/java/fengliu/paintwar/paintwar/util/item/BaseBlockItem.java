package fengliu.paintwar.paintwar.util.item;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 默认自动生成 "item/" 下的 "parent" 指向块模型, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateItemModels(ItemModelGenerator) generateItemModels}
 */
public class BaseBlockItem extends BlockItem implements IModItem {
    private final String tooltipKey;

    public <B extends Block & IModBlock> BaseBlockItem(B block) {
        super(block, new FabricItemSettings().maxCount(64));
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, block.getBlockName());
    }

    public <B extends Block & IModBlock> BaseBlockItem(B block, Settings settings) {
        super(block, settings);
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, block.getBlockName());
    }

    @Override
    public String getItemName() {
        return ((BaseBlock) this.getBlock()).getBlockName();
    }

    @Override
    public String getTextureName() {
        return ((BaseBlock) this.getBlock()).getTextureName();
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

