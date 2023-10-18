package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.BlockSetType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ColorDoorBlock extends NoColorDoorBlock implements IColor {
    private final DyeColor color;
    private final String blockName;

    public ColorDoorBlock(Settings settings, BlockSetType blockSetType, DyeColor color, String name) {
        super(settings, blockSetType, name);
        this.color = color;
        this.blockName = color.getName() + "_" + name;
    }

    @Override
    public Identifier getModelId() {
        return ModBlocks.NO_COLOR_DOOR_BLOCK.getModelId();
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public String getBlockName() {
        return this.blockName;
    }
}
