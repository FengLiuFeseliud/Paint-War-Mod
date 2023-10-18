package fengliu.paintwar.paintwar.util.entity;

import fengliu.paintwar.paintwar.mixin.LivingEntityMixin;
import net.minecraft.util.DyeColor;

/**
 * Mod 生命实体 Mixin 接口
 */
public interface IModLivingEntity {

    /**
     * 获取实体火焰颜色, null 表示使用原版火焰, 参考 {@link LivingEntityMixin#getFireColor()}
     */
    DyeColor getFireColor();

    /**
     * 设置实体火焰, 参考 {@link LivingEntityMixin#setFireColor(DyeColor)}
     */
    void setFireColor(DyeColor color);
}
