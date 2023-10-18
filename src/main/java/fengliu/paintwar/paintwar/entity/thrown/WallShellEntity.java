package fengliu.paintwar.paintwar.entity.thrown;

import fengliu.paintwar.paintwar.entity.ModEntitys;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.sound.ModSoundEvents;
import fengliu.paintwar.paintwar.util.ShapeUtil;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.entity.ColorItemThrownEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WallShellEntity extends ColorItemThrownEntity {
    public static final int HEIGHT_SIZE = 4;
    public static final int WIDTH_SIZE = 7;

    private static final TrackedData<NbtCompound> BLOCKS;
    public static final String BLOCKS_KEY = "blocks";

    static {
        BLOCKS = DataTracker.registerData(WallShellEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    }


    public WallShellEntity(EntityType<ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public WallShellEntity(PlayerEntity player, World world, List<Block> wallBlocks) {
        super(ModEntitys.WALL_SHELL_ENTITY_TYPE, player, world);
        this.setWallBlocks(wallBlocks);
    }

    @Override
    public List<? extends IColor> getColorItems() {
        return ModItems.WALL_SHELLS;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(BLOCKS, new NbtCompound());
    }

    public List<Block> getWallBlocks(){
        return this.getDataTracker().get(BLOCKS).getList(BLOCKS_KEY, NbtElement.STRING_TYPE).stream()
                .map(path -> Registries.BLOCK.get(Identifier.tryParse(path.asString()))).toList();
    }

    public void setWallBlocks(NbtList nbtList){
        NbtCompound nbt = new NbtCompound();
        nbt.put(BLOCKS_KEY, nbtList);
        this.dataTracker.set(BLOCKS, nbt);
    }

    public void setWallBlocks(List<Block> wallBlocks){
        NbtCompound nbt = new NbtCompound();
        NbtList nbtList = new NbtList();

        wallBlocks.forEach(block -> nbtList.add(NbtString.of(Registries.BLOCK.getId(block).toString())));
        nbt.put(BLOCKS_KEY, nbtList);
        this.dataTracker.set(BLOCKS, nbt);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.world.playSound(null, hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ(), ModSoundEvents.ENTITY_LAND_WALL_SHELL, SoundCategory.PLAYERS, 0.5F, 1.0F);
        if (this.world.isClient || hitResult.getType() == BlockHitResult.Type.ENTITY){
            return;
        }

        final int[] index = {0};
        List<Block> blocks = this.getWallBlocks();
        ShapeUtil.quadrilateral(WIDTH_SIZE, HEIGHT_SIZE, BlockPos.ofFloored(hitResult.getPos()), this.getMovementDirection(),
                pos -> {
                    try {
                        this.getWorld().setBlockState(pos, blocks.get(index[0]).getDefaultState());
                        this.world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 0.5F, 1.0F);
                    } catch (ArrayIndexOutOfBoundsException err){
                        return;
                    }
                    index[0]++;
                });
        this.kill();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put(BLOCKS_KEY, this.getDataTracker().get(BLOCKS).getList(BLOCKS_KEY, NbtElement.STRING_TYPE));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setWallBlocks(nbt.getList(BLOCKS_KEY, NbtElement.STRING_TYPE));
    }
}
