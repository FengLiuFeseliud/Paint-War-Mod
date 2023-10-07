package fengliu.paintwar.paintwar.util.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class ColorItemPersistentThrownEntity extends ColorItemThrownEntity{
    private static final TrackedData<Integer> DURATION_TICK = DataTracker.registerData(ColorItemPersistentThrownEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> FALL = DataTracker.registerData(ColorItemPersistentThrownEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ColorItemPersistentThrownEntity(EntityType<ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ColorItemPersistentThrownEntity(EntityType<ThrownItemEntity> entityType, PlayerEntity player, World world) {
        super(entityType, player, world);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(DURATION_TICK, this.getMaxDurationTick());
        this.getDataTracker().startTracking(FALL, false);
    }

    public abstract int getMaxDurationTick();

    public int getDurationTick(){
        return this.getDataTracker().get(DURATION_TICK);
    }

    private void setDurationTick(int tick){
        this.getDataTracker().set(DURATION_TICK, tick);
    }

    public void dropDurationTick(){
        this.getDataTracker().set(DURATION_TICK, this.getDurationTick() - 1);
    }

    public boolean getFall(){
        return this.getDataTracker().get(FALL);
    }

    public void setFall(boolean fallIn){
        this.getDataTracker().set(FALL, fallIn);
    }

    @Override
    public void tick() {
        if (!this.getFall()){
            super.tick();
            return;
        }

        if (this.getDurationTick() == 0){
            this.kill();
        } else {
            this.dropDurationTick();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (hitResult.getType() != HitResult.Type.BLOCK){
            return;
        }

        this.setFall(true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("durationTick", this.getDurationTick());
        nbt.putBoolean("fall", this.getFall());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setDurationTick(nbt.getInt("smokeTick"));
        this.setFall(nbt.getBoolean("fall"));
    }
}
