package fengliu.paintwar.paintwar.data;

import fengliu.paintwar.paintwar.util.IdUtil;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.Identifier;

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
}
