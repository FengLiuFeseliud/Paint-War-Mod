package fengliu.paintwar.paintwar.util.color;

import fengliu.paintwar.paintwar.util.RegisterUtil;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;

/**
 * 实现 {@link IColor} 接口表示该 物品/方块 有多个颜色, 将在数据生成时创建不同颜色的版本
 * <p>
 * 数据生成参考 {@link fengliu.paintwar.paintwar.data.generation.LangGeneration#generateTranslations(FabricLanguageProvider.TranslationBuilder) generateTranslations}
 * <p>
 * 实现 {@link IColor} 接口的物品使用 {@link fengliu.paintwar.paintwar.util.RegisterUtil#registerColorItems(RegisterUtil.colorItem, ItemGroup) registerColorItems} 注册
 * <p>
 * 实现 {@link IColor} 接口的块使用 {@link fengliu.paintwar.paintwar.util.RegisterUtil#registerColorBlocks(RegisterUtil.colorBlock) registerColorBlocks} 注册
 * <p>
 * 全部使用灰度图实现 物品/方块 的不同颜色
 */
public interface IColor {
    /**
     * 获取颜色
     * @return 颜色
     */
    DyeColor getColor();

    /**
     * 获取材质名, 返回不包含颜色的材质名, 以将所有颜色指向同一个灰度图材质与模型
     * @return 材质名
     */
    String getTextureName();
}
