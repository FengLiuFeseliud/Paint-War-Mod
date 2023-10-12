package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.BlockSetType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ColorDoorBlock extends NoColorDoorBlock implements IColor {
    public ColorDoorBlock(Settings settings, BlockSetType blockSetType, DyeColor color, String name) {
        super(settings, blockSetType, color, name);
    }

    @Override
    public Identifier getModelId() {
        return ModBlocks.NO_COLOR_DOOR_BLOCK.getModelId();
    }
}
