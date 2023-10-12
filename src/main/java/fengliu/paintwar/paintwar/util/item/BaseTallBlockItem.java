package fengliu.paintwar.paintwar.util.item;

import fengliu.paintwar.paintwar.util.block.BaseBlock;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TallBlockItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseTallBlockItem extends TallBlockItem implements IModItem {
    private final String tooltipKey;

    public <B extends Block & IModBlock> BaseTallBlockItem(B block) {
        super(block, new FabricItemSettings().maxCount(64));
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, block.getBlockName());
    }

    public <B extends Block & IModBlock> BaseTallBlockItem(B block, Settings settings) {
        super(block, settings);
        this.tooltipKey = BaseBlockItem.getTooltipKey(block, block.getBlockName());
    }

    @Override
    public String getItemName() {
        return ((IModBlock) this.getBlock()).getBlockName();
    }

    @Override
    public String getTextureName() {
        return ((IModBlock) this.getBlock()).getTextureName();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
