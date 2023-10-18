package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.data.ModModels;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.util.DyeColor;

public class NoColorTrapdoorBlock extends TrapdoorBlock implements IModBlock {
    private final String blockName;
    private final String textureName;

    public NoColorTrapdoorBlock(Settings settings, BlockSetType blockSetType, String name) {
        super(settings, blockSetType);
        this.blockName = name;
        this.textureName = name;
    }

    @Override
    public String getBlockName() {
        return this.blockName;
    }

    @Override
    public String getTextureName() {
        return textureName;
    }

    @Override
    public boolean canSprayBlock() {
        return true;
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        ModModels.registerColorTrapdoor(this, this.getModelId(), blockStateModelGenerator);
    }
}
