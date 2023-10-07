package fengliu.paintwar.paintwar.util.entity;

import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

import java.util.List;

public abstract class ColorItemThrownEntity extends ThrownItemEntity {
    private static final TrackedData<Integer> COLOR;

    static {
        COLOR = DataTracker.registerData(ColorItemThrownEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public ColorItemThrownEntity(EntityType<ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ColorItemThrownEntity(EntityType<ThrownItemEntity> entityType, PlayerEntity user, World world) {
        super(entityType, user, world);
    }

    public abstract List<BaseItem> getColorItems();

    @Override
    protected Item getDefaultItem() {
        return this.getColorItems().get(0);
    }

    @Override
    public ItemStack getStack() {
        for(Item item: this.getColorItems()){
            if (!((IColor) item).getColor().equals(this.getColor())){
                continue;
            }

            return new ItemStack(item);
        }
        return new ItemStack(this.getDefaultItem());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(COLOR, DyeColor.WHITE.getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.dataTracker.get(COLOR));
    }

    public void setColor(DyeColor color) {
        if (color == null){
            this.dataTracker.set(COLOR, -1);
            return;
        }
        this.dataTracker.set(COLOR, color.getId());
    }

    @Override
    public void setItem(ItemStack item) {
        super.setItem(item);
        this.setColor(((IColor) item.getItem()).getColor());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("color", this.getColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        int colorId = nbt.getInt("color");
        if (colorId == -1){
            this.setColor(null);
            return;
        }
        this.setColor(DyeColor.byId(colorId));
    }
}
