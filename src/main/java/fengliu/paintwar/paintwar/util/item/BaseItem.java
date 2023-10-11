package fengliu.paintwar.paintwar.util.item;

import fengliu.paintwar.paintwar.util.IdUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;

/**
 * 默认自动生成 "item/" 下的 generated 单层物品模型, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateItemModels(ItemModelGenerator) generateItemModels}
 */
public class BaseItem extends Item implements IModItem{
    public final String name;
    public final String tooltipKey;
    public final String textureName;

    public BaseItem(Settings settings, String name) {
        super(settings);
        this.name = name;
        this.textureName = name;
        this.tooltipKey = IdUtil.getItemTooltip(name);
    }

    public BaseItem(String name) {
        this(new Settings().maxCount(64), name);
    }

    public BaseItem(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings);
        this.name = dyeColor.getName() + "_" + textureName;
        this.textureName = textureName;
        this.tooltipKey = IdUtil.getItemTooltip(textureName);
    }

    @Override
    public String getItemName() {
        return this.name;
    }

    @Override
    public String getTextureName() {
        return this.textureName;
    }

    public BaseItem(DyeColor dyeColor, String textureName) {
        this(new Settings().maxCount(64), dyeColor, textureName);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
