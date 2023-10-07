package fengliu.paintwar.paintwar.entity.thrown;

import fengliu.paintwar.paintwar.entity.ModEntitys;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.entity.ColorItemPersistentThrownEntity;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

public class SignalShellEntity extends ColorItemPersistentThrownEntity {
    public static final int MAX_DURATION = 600;
    public static final int CHANGE_TICK = 14;
    public static final int SIGNAL_HEIGHT = 8;

    public SignalShellEntity(EntityType<ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SignalShellEntity(PlayerEntity player, World world) {
        super(ModEntitys.SIGNAL_SHELL_ENTITY_TYPE, player, world);
    }

    @Override
    public int getMaxDurationTick() {
        return MAX_DURATION;
    }

    @Override
    public List<BaseItem> getColorItems() {
        return ModItems.SIGNAL_SHELLS;
    }

    @Override
    public void tick() {
        super.tick();

        if (!getFall()){
            world.addParticle(new DustParticleEffect(Vec3d.unpackRgb(this.getColor().getFireworkColor()).toVector3f(), 4F),
                    this.getX(), this.getY() , this.getZ(), 1, 10, 1);
            return;
        }

        if (this.getDurationTick() % 6 != 0){
            return;
        }

        BlockPos fallPos = this.getBlockPos().up();
        Vector3f color = Vec3d.unpackRgb(this.getColor().getFireworkColor()).toVector3f();

        int durationTick = this.getDurationTick();
        for (int index = 0; index < SIGNAL_HEIGHT; index ++){
            if (index * CHANGE_TICK < durationTick && durationTick > MAX_DURATION - index * CHANGE_TICK){
                continue;
            }

            world.addParticle(new DustParticleEffect(color, 4F),
                    fallPos.getX(), fallPos.getY() , fallPos.getZ(), 1, 10, 1);
            fallPos = fallPos.up();
        }

    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }
}
