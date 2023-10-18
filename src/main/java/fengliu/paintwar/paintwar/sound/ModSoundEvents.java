package fengliu.paintwar.paintwar.sound;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.RegisterUtil;
import net.minecraft.sound.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent ITEM_USE_BLOCK_PAINT_FIRE_GENERATOR = RegisterUtil.registerSoundEvent(IdUtil.get("item.use.block_paint_fire_generator"), SoundEvent::of);
    public static final SoundEvent ITEM_USE_SPRAY_GUN = RegisterUtil.registerSoundEvent(IdUtil.get("item.use.spray_gun"), SoundEvent::of);
    public static final SoundEvent ITEM_USE_BRUSH = RegisterUtil.registerSoundEvent(IdUtil.get("item.use.brush"), SoundEvent::of);
    public static final SoundEvent ITEM_USE_COLOR_PICKER = RegisterUtil.registerSoundEvent(IdUtil.get("item.use.color_picker"), SoundEvent::of);
    public static final SoundEvent ITEM_USE_FLARE_GUN = RegisterUtil.registerSoundEvent(IdUtil.get("item.use.flare_gun"), SoundEvent::of);

    public static final SoundEvent ENTITY_THROW_BALLOON = RegisterUtil.registerSoundEvent(IdUtil.get("entity.throw.balloon"), SoundEvent::of);
    public static final SoundEvent ENTITY_THROW_LAND_BALLOON = RegisterUtil.registerSoundEvent(IdUtil.get("entity.throw.land.balloon"), SoundEvent::of);

    public static final SoundEvent ENTITY_THROW_BOMB = RegisterUtil.registerSoundEvent(IdUtil.get("entity.throw.bomb"), SoundEvent::of);
    public static final SoundEvent ENTITY_LAND_SMOKE_BOMB = RegisterUtil.registerSoundEvent(IdUtil.get("entity.land.smoke_bomb"), SoundEvent::of);
    public static final SoundEvent ENTITY_SMOKE_BOMB_HISS = RegisterUtil.registerSoundEvent(IdUtil.get("entity.smoke_bomb_hiss"), SoundEvent::of);

    public static final SoundEvent ENTITY_LAND_FLARE_SHELL = RegisterUtil.registerSoundEvent(IdUtil.get("entity.land.flare_shell"), SoundEvent::of);
    public static final SoundEvent ENTITY_FLARE_SHELL_HISS = RegisterUtil.registerSoundEvent(IdUtil.get("entity.flare_shell_hiss"), SoundEvent::of);

    public static final SoundEvent ENTITY_LAND_WALL_SHELL = RegisterUtil.registerSoundEvent(IdUtil.get("entity.land.wall_shell"), SoundEvent::of);

    public static void registerAllSoundEvent(){

    }
}
