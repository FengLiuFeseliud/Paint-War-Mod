package fengliu.paintwar.paintwar.data;

import com.google.common.collect.ImmutableList;
import fengliu.paintwar.paintwar.util.IdUtil;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class ModModels {
    public static final Model COLOR_DOOR_BOTTOM_LEFT = block("color_door", "_bottom_left", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_BOTTOM_LEFT_OPEN = block("color_door", "_bottom_left_open", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_BOTTOM_RIGHT = block("color_door", "_bottom_right", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_BOTTOM_RIGHT_OPEN = block("color_door", "_bottom_right_open", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_TOP_LEFT = block("color_door", "_top_left", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_TOP_LEFT_OPEN = block("color_door", "_top_left_open", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_TOP_RIGHT = block("color_door", "_top_right", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_DOOR_TOP_RIGHT_OPEN = block("color_door", "_top_right_open", TextureKey.TOP, TextureKey.BOTTOM);
    public static final Model COLOR_TRAPDOOR_TOP = block("color_trapdoor", "_top", TextureKey.TEXTURE);
    public static final Model COLOR_TRAPDOOR_BOTTOM= block("color_trapdoor", "_bottom", TextureKey.TEXTURE);
    public static final Model COLOR_TRAPDOOR_OPEN = block("color_trapdoor", "_open", TextureKey.TEXTURE);
    public static final Model COLOR_FIRE_FLOOR = block("color_fire", "_floor", TextureKey.FIRE);
    public static final Model COLOR_FIRE_SIDE = block("color_fire", "_side", TextureKey.FIRE);
    public static final Model COLOR_FIRE_SIDE_ALT = block("color_fire", "_side_alt", TextureKey.FIRE);


    private static Model block(String head, String trail, TextureKey... requiredTextureKeys){
        StringBuilder stringBuffer = new StringBuilder("block/template/");
        return new Model(Optional.of(IdUtil.get(stringBuffer.append(head).append("/").append(head).append(trail).toString())), Optional.of(trail), requiredTextureKeys);
    }

    public static void registerColorDoor(DoorBlock doorBlock, Identifier modelId, BlockStateModelGenerator blockStateModelGenerator){
        TextureMap textureMap = TextureMap.topBottom(new Identifier(modelId.getNamespace(), modelId.getPath() + "_top"), new Identifier(modelId.getNamespace(), modelId.getPath() + "_bottom"));
        blockStateModelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createDoorBlockState(
                        doorBlock,
                        COLOR_DOOR_BOTTOM_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_BOTTOM_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_BOTTOM_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_BOTTOM_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_TOP_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_TOP_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_TOP_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_DOOR_TOP_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector)
                ));
    }

    public static void registerColorTrapdoor(TrapdoorBlock trapdoorBlock, Identifier modelId, BlockStateModelGenerator blockStateModelGenerator){
        TextureMap textureMap = TextureMap.texture(modelId);
        blockStateModelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createOrientableTrapdoorBlockState(
                        trapdoorBlock,
                        COLOR_TRAPDOOR_TOP.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_TRAPDOOR_BOTTOM.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector),
                        COLOR_TRAPDOOR_OPEN.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector)
                ));
    }

    public static void registerFire(AbstractFireBlock fireBlock, Identifier modelId, BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap fire0 = (new TextureMap()).put(TextureKey.FIRE, modelId);
        TextureMap fire1 = (new TextureMap()).put(TextureKey.FIRE, modelId);

        Identifier identifier = COLOR_FIRE_FLOOR.upload(ModelIds.getBlockSubModelId(fireBlock, "_floor0"), fire0, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = COLOR_FIRE_FLOOR.upload(ModelIds.getBlockSubModelId(fireBlock, "_floor1"), fire1, blockStateModelGenerator.modelCollector);
        List<Identifier> list = ImmutableList.of(identifier, identifier2);

        identifier = COLOR_FIRE_SIDE.upload(ModelIds.getBlockSubModelId(fireBlock, "_side0"), fire0, blockStateModelGenerator.modelCollector);
        identifier2 = COLOR_FIRE_SIDE.upload(ModelIds.getBlockSubModelId(fireBlock, "_side1"), fire1, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = COLOR_FIRE_SIDE_ALT.upload(ModelIds.getBlockSubModelId(fireBlock, "_side_alt0"), fire0, blockStateModelGenerator.modelCollector);
        Identifier identifier4 = COLOR_FIRE_SIDE_ALT.upload(ModelIds.getBlockSubModelId(fireBlock, "_side_alt1"), fire1, blockStateModelGenerator.modelCollector);
        List<Identifier> list2 = ImmutableList.of(identifier, identifier2, identifier3, identifier4);

        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(fireBlock).with(BlockStateModelGenerator.buildBlockStateVariants(list,
                (blockStateVariant) -> blockStateVariant)).with(BlockStateModelGenerator.buildBlockStateVariants(list2, (blockStateVariant) -> blockStateVariant))
                .with(BlockStateModelGenerator.buildBlockStateVariants(list2, (blockStateVariant) -> blockStateVariant.put(VariantSettings.Y, VariantSettings.Rotation.R90)))
                .with(BlockStateModelGenerator.buildBlockStateVariants(list2, (blockStateVariant) -> blockStateVariant.put(VariantSettings.Y, VariantSettings.Rotation.R180)))
                .with(BlockStateModelGenerator.buildBlockStateVariants(list2, (blockStateVariant) -> blockStateVariant.put(VariantSettings.Y, VariantSettings.Rotation.R270)))
        );
    }
}
