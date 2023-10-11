package fengliu.paintwar.paintwar.util.block;

import fengliu.paintwar.paintwar.util.IdUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;


public class BaseBlock extends Block implements IModBlock {
    private final String textureName;
    private final String blockName;
    private final DyeColor color;

    public BaseBlock(AbstractBlock.Settings settings, String name) {
        super(settings);
        this.textureName = name;
        this.blockName = name;
        this.color = null;
    }

    public BaseBlock(AbstractBlock.Settings settings, DyeColor color, String name) {
        super(settings);
        this.textureName = name;
        this.blockName = color.getName() + "_" + name;
        this.color = color;
    }

    @Override
    public String getBlockName() {
        return this.blockName;
    }

    @Override
    public String getTextureName() {
        return this.textureName;
    }

    public DyeColor getColor(){
        return this.color;
    }

}
