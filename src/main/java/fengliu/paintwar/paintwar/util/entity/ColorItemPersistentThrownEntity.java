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

/**
 * 16色物品可停留实物, 这个实物可以落地后存在自定义 tick 的时间
 */
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

    /**
     * 最大存在 tick
     * @return 最大存在 tick
     */
    public abstract int getMaxDurationTick();

    /**
     * 当前剩于存在 tick
     * @return 剩于 tick
     */
    public int getDurationTick(){
        return this.getDataTracker().get(DURATION_TICK);
    }

    /**
     * 设置剩于存在 tick, 用于从 {@link ColorItemPersistentThrownEntity#readCustomDataFromNbt(NbtCompound) readCustomDataFromNbt} 设置剩于存在 tick
     * @param tick 剩于 tick
     */
    private void setDurationTick(int tick){
        this.getDataTracker().set(DURATION_TICK, tick);
    }

    /**
     * 剩于存在 tick减一
     */
    public void dropDurationTick(){
        this.getDataTracker().set(DURATION_TICK, this.getDurationTick() - 1);
    }

    /**
     * 获取是否落地
     * @return true, 落地
     */
    public boolean getFall(){
        return this.getDataTracker().get(FALL);
    }

    /**
     * 设置是否落地
     * @param fallIn true, 落地
     */
    public void setFall(boolean fallIn){
        this.getDataTracker().set(FALL, fallIn);
    }

    /**
     * 如果落地, 开始  tick 减一
     */
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

        // 落地
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
