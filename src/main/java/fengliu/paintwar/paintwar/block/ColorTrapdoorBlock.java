package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ColorTrapdoorBlock extends NoColorTrapdoorBlock implements IColor {
    private final DyeColor color;
    private final String blockName;

    public ColorTrapdoorBlock(Settings settings, BlockSetType blockSetType, DyeColor color, String name) {
        super(settings, blockSetType, name);
        this.blockName = color.getName() + "_" + name;
        this.color = color;
    }

    @Override
    public Identifier getModelId() {
        return ModBlocks.NO_COLOR_TRAPDOOR_BLOCK.getModelId();
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
