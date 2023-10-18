package fengliu.paintwar.paintwar.mixin;

import fengliu.paintwar.paintwar.util.entity.IModLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IModLivingEntity {
    @Unique
    private static final String FIRE_COLOR_KEY = "fireColor";
    @Unique
    private static final TrackedData<Integer> FIRE_COLOR = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    public void initDataTracker(CallbackInfo ci){
        this.getDataTracker().startTracking(FIRE_COLOR, -1);
    }

    @Override
    public DyeColor getFireColor() {
        int colorId = this.getDataTracker().get(FIRE_COLOR);
        if (colorId == -1){
            return null;
        }
        return DyeColor.byId(colorId);
    }

    @Override
    public void setFireColor(DyeColor color) {
        if (color == null){
            this.getDataTracker().set(FIRE_COLOR, -1);
            return;
        }
        this.getDataTracker().set(FIRE_COLOR, color.getId());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.getDataTracker().set(FIRE_COLOR, nbt.getInt(FIRE_COLOR_KEY));
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt(FIRE_COLOR_KEY, this.getDataTracker().get(FIRE_COLOR));
    }
}
