package fengliu.paintwar.paintwar.entity.thrown;

import fengliu.paintwar.paintwar.entity.ModEntitys;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.item.tool.Brush;
import fengliu.paintwar.paintwar.sound.ModSoundEvents;
import fengliu.paintwar.paintwar.util.ShapeUtil;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.entity.ColorItemThrownEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;


public class ColorWaterBalloonEntity extends ColorItemThrownEntity {
    public static final int HIT_BLOCK_SPRAY_SIZE = 5;
    public static final int HIT_ENTITY_SPRAY_SIZE = 3;
    public static final int DARKNESS_TIME = 80;

    public ColorWaterBalloonEntity(EntityType<ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ColorWaterBalloonEntity(PlayerEntity player, World world) {
        super(ModEntitys.COLOR_WATER_BALLOON_ENTITY_TYPE, player, world);
    }

    @Override
    public List<? extends IColor> getColorItems() {
        return ModItems.COLOR_WATER_BALLOONS;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.world.playSound(null, hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ(), ModSoundEvents.ENTITY_THROW_LAND_BALLOON, SoundCategory.BLOCKS, 0.5F, 1.0F);
        if (this.world.isClient || hitResult.getType() == HitResult.Type.ENTITY){
            return;
        }

        ShapeUtil.rhombus(HIT_BLOCK_SPRAY_SIZE, BlockPos.ofFloored(hitResult.getPos()), this.getMovementDirection(), this.getWorld(),
                pos -> Brush.spray(this.getWorld(), pos, Brush.sprayBlock(this.getWorld(), pos, this.getWorld().getBlockState(pos), this.getColor())));
        this.kill();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.world.playSound(null, entityHitResult.getPos().getX(), entityHitResult.getPos().getY(), entityHitResult.getPos().getZ(), ModSoundEvents.ENTITY_THROW_LAND_BALLOON, SoundCategory.BLOCKS, 0.5F, 1.0F);
        ShapeUtil.rhombus(HIT_ENTITY_SPRAY_SIZE, entityHitResult.getEntity().getBlockPos(), this.getMovementDirection(), this.getWorld(),
                pos -> Brush.spray(this.getWorld(), pos, Brush.sprayBlock(this.getWorld(), pos, this.getWorld().getBlockState(pos), this.getColor())));
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity){
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, DARKNESS_TIME, 1), this.getOwner());
        }
        this.kill();
    }
}
