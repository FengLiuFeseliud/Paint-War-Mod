package fengliu.paintwar.paintwar.entity;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.entity.thrown.ColorWaterBalloonEntity;
import fengliu.paintwar.paintwar.entity.thrown.WaterBalloonEntity;
import fengliu.paintwar.paintwar.entity.thrown.WallShellEntity;
import fengliu.paintwar.paintwar.entity.thrown.PaintSmokeBombEntity;
import fengliu.paintwar.paintwar.entity.thrown.SignalShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntitys {
    public static final EntityType<ThrownItemEntity> COLOR_WATER_BALLOON_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("color_water_balloon"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, ColorWaterBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

    public static final EntityType<ThrownItemEntity> WATER_BALLOON_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("water_balloon"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, WaterBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

    public static final EntityType<ThrownItemEntity> WALL_SHELL_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("wall_shell_entity"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, WallShellEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

    public static final EntityType<ThrownItemEntity> PAINT_SMOKE_BOMB_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("paint_smoke_bomb_entity"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, PaintSmokeBombEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

    public static final EntityType<ThrownItemEntity> SIGNAL_SHELL_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("signal_shell_entity"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, SignalShellEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());


    @Environment(EnvType.CLIENT)
    public static void registerAllEntityRenderer(){
        EntityRendererRegistry.register(ModEntitys.COLOR_WATER_BALLOON_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntitys.WATER_BALLOON_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntitys.WALL_SHELL_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntitys.PAINT_SMOKE_BOMB_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntitys.SIGNAL_SHELL_ENTITY_TYPE, FlyingItemEntityRenderer::new);
    }

    public static void registerAllEntity(){}
}
