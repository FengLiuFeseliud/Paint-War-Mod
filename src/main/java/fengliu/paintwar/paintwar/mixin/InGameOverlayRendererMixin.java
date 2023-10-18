package fengliu.paintwar.paintwar.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import fengliu.paintwar.paintwar.block.ColorFireBlock;
import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.entity.IModLivingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Unique
    private static final SpriteIdentifier COLOR_FIRE_SPRITE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, IdUtil.get("block/color_fire"));

    @Unique
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Redirect(
            method = "renderFireOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/SpriteIdentifier;getSprite()Lnet/minecraft/client/texture/Sprite;"
            )
    )
    private static Sprite setOverlayFire(SpriteIdentifier instance){
        assert client.player != null;
        DyeColor fireColor = ((IModLivingEntity) client.player).getFireColor();
        if (fireColor == null){
            return ModelLoader.FIRE_1.getSprite();
        }

        RenderSystem.setShaderColor(fireColor.getColorComponents()[0], fireColor.getColorComponents()[1], fireColor.getColorComponents()[2], 1.0F);
        return COLOR_FIRE_SPRITE.getSprite();
    }
}
