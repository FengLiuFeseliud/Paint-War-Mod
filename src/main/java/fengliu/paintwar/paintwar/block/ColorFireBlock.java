package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.data.ModModels;
import fengliu.paintwar.paintwar.mixin.EntityMixin;
import fengliu.paintwar.paintwar.mixin.EntityRenderDispatcherMixin;
import fengliu.paintwar.paintwar.mixin.InGameOverlayRendererMixin;
import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.entity.IModLivingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 颜料火焰方块, 实现方块 Mixin 了以下内容
 * <p>
 * 实体保存着火火焰颜色, 参考 {@link IModLivingEntity#setFireColor(DyeColor)}, {@link IModLivingEntity#getFireColor()}
 * <p>
 * 玩家火焰覆盖图设置颜色, 参考 {@link InGameOverlayRendererMixin#setOverlayFire(SpriteIdentifier)}
 * <p>
 * 实体修改着火火焰颜色, 参考 {@link EntityRenderDispatcherMixin#setOverlayFire(SpriteIdentifier)}, {@link EntityRenderDispatcherMixin#setFireColor(MatrixStack.Entry, VertexConsumer, float, float, float, float, float)}
 * <p>
 * 实体着颜料火时修改脚下方块, 参考 {@link EntityMixin#colorFireSprayBlock(CallbackInfo)}
 */
public class ColorFireBlock extends AbstractFireBlock implements IModBlock, IColor {
    private final DyeColor color;
    private final String textureName;
    private final String blockName;

    public ColorFireBlock(Settings settings, float damage, DyeColor dyeColor, String name) {
        super(settings, damage);
        this.color = dyeColor;
        this.textureName = name;
        this.blockName = color.getName() + "_" + name;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public String getBlockName() {
        return this.blockName;
    }

    @Override
    public String getTextureName() {
        return this.textureName;
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        ModModels.registerFire(this, this.getModelId(), blockStateModelGenerator);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.getBlock() instanceof AbstractFireBlock && world.getBlockState(pos.down()).getBlock() instanceof AbstractFireBlock){
            return false;
        }

        return Registries.BLOCK.getId(state.getBlock()).getPath().startsWith(this.getColor().getName());
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setOnFireFor(8);
        }

        try {
            super.onEntityCollision(state, world, pos, entity);
        } catch (ClassCastException ignored){}
    }
}
