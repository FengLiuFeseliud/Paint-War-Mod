package fengliu.paintwar.paintwar.mixin;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.entity.IModLivingEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Unique
    private static final SpriteIdentifier COLOR_FIRE_SPRITE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, IdUtil.get("block/color_fire"));
    @Unique
    private DyeColor fireColor;

    @Inject(method = "renderFire", at = @At("HEAD"))
    public void getFireColor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, CallbackInfo ci){
        if (!(entity instanceof LivingEntity)){
            return;
        }

        this.fireColor = ((IModLivingEntity) entity).getFireColor();
    }

    @Redirect(
            method = "renderFire",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/SpriteIdentifier;getSprite()Lnet/minecraft/client/texture/Sprite;"
            )
    )
    public Sprite setOverlayFire(SpriteIdentifier instance){
        if (this.fireColor == null){
            return ModelLoader.FIRE_1.getSprite();
        }

        return COLOR_FIRE_SPRITE.getSprite();
    }

    @Redirect(
            method = "renderFire",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;drawFireVertex(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumer;FFFFF)V"
            )
    )
    public void setFireColor(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
        if (this.fireColor == null){
            vertices.vertex(entry.getPositionMatrix(), x, y, z)
                    .color(255, 255, 255, 255)
                    .texture(u, v)
                    .overlay(0, 10)
                    .light(240)
                    .normal(entry.getNormalMatrix(), 0.0F, 1.0F, 0.0F)
                    .next();
            return;
        }
        vertices.vertex(entry.getPositionMatrix(), x, y, z)
                .color(this.fireColor.getColorComponents()[0], this.fireColor.getColorComponents()[1], this.fireColor.getColorComponents()[2], 255)
                .texture(u, v)
                .overlay(0, 10)
                .light(240).normal(entry.getNormalMatrix(), 0.0F, 1.0F, 0.0F)
                .next();
    }
}
