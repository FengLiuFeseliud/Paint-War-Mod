package fengliu.paintwar.paintwar.mixin;

import fengliu.paintwar.paintwar.item.tool.Brush;
import fengliu.paintwar.paintwar.util.entity.IModLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract World getWorld();

    @Shadow public abstract BlockPos getBlockPos();

    @Shadow public abstract Direction getHorizontalFacing();

    @Shadow public abstract Direction getMovementDirection();

    @Inject(
            method = "baseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    public void colorFireSprayBlock(CallbackInfo ci){
        if (!((Object) this instanceof LivingEntity entity)){
            return;
        }

        BlockPos blockPos = this.getBlockPos().down();
        Brush.spray(this.getWorld(), blockPos, Brush.sprayBlock(this.getWorld(), blockPos, this.getWorld().getBlockState(blockPos), ((IModLivingEntity) entity).getFireColor()));
        blockPos = this.getBlockPos().down().south();
        Brush.spray(this.getWorld(), blockPos, Brush.sprayBlock(this.getWorld(), blockPos, this.getWorld().getBlockState(blockPos), ((IModLivingEntity) entity).getFireColor()));
        blockPos = this.getBlockPos().down().north();
        Brush.spray(this.getWorld(), blockPos, Brush.sprayBlock(this.getWorld(), blockPos, this.getWorld().getBlockState(blockPos), ((IModLivingEntity) entity).getFireColor()));
        blockPos = this.getBlockPos().down().west();
        Brush.spray(this.getWorld(), blockPos, Brush.sprayBlock(this.getWorld(), blockPos, this.getWorld().getBlockState(blockPos), ((IModLivingEntity) entity).getFireColor()));
        blockPos = this.getBlockPos().down().east();
        Brush.spray(this.getWorld(), blockPos, Brush.sprayBlock(this.getWorld(), blockPos, this.getWorld().getBlockState(blockPos), ((IModLivingEntity) entity).getFireColor()));
    }
}
