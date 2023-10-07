package fengliu.paintwar.paintwar.entity.thrown;

import fengliu.paintwar.paintwar.entity.ModEntitys;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.ShapeUtil;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static fengliu.paintwar.paintwar.entity.thrown.ColorWaterBalloonEntity.HIT_BLOCK_SPRAY_SIZE;
import static fengliu.paintwar.paintwar.entity.thrown.ColorWaterBalloonEntity.HIT_ENTITY_SPRAY_SIZE;


public class WaterBalloonEntity extends ThrownItemEntity {

    public static final int DARKNESS_TIME = 80;

    public WaterBalloonEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public WaterBalloonEntity(PlayerEntity player, World world) {
        super(ModEntitys.WATER_BALLOON_ENTITY_TYPE, player, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.WATER_BALLOON;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.world.isClient || hitResult.getType() == HitResult.Type.ENTITY){
            return;
        }

        ShapeUtil.rhombus(HIT_BLOCK_SPRAY_SIZE, BlockPos.ofFloored(hitResult.getPos()), this.getMovementDirection(), this.getWorld(),
                pos -> this.getWorld().setBlockState(pos.up(), Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 3)));
        this.kill();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        ShapeUtil.rhombus(HIT_ENTITY_SPRAY_SIZE, entityHitResult.getEntity().getBlockPos(), this.getMovementDirection(), this.getWorld(),
                pos -> this.getWorld().setBlockState(pos.up(), Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 3)));
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity){
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, DARKNESS_TIME, 1), this.getOwner());
        }
        this.kill();
    }
}
