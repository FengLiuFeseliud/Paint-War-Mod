package fengliu.paintwar.paintwar.mixin;

import fengliu.paintwar.paintwar.block.ColorFireBlock;
import fengliu.paintwar.paintwar.util.entity.IModLivingEntity;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public abstract class FireBlockMixin extends Block {
    public FireBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onEntityCollision", at = @At("RETURN"))
    public void setEntityFireColor(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof LivingEntity)){
            return;
        }

        if (!(state.getBlock() instanceof ColorFireBlock colorFire)){
            ((IModLivingEntity) entity).setFireColor(null);
            return;
        }

        ((IModLivingEntity) entity).setFireColor(colorFire.getColor());
    }

    @Redirect(
            method = "randomDisplayTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
            )
    )
    public void setParticleColor(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ){
        if (!((Object) this instanceof ColorFireBlock colorFire)){
            world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, velocityX, velocityY, velocityZ);
            return;
        }
        world.addParticle(new DustParticleEffect(Vec3d.unpackRgb(colorFire.getColor().getFireworkColor()).toVector3f(), 4F), x, y, z, velocityX, velocityY, velocityZ);
    }
}
