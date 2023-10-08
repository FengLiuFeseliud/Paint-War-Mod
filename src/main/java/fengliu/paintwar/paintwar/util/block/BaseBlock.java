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


public class BaseBlock extends Block {
    public static final String PREFIXED_PATH = "block/";
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

    public String getBlockName() {
        return this.blockName;
    }

    public DyeColor getColor(){
        return this.color;
    }

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    public String getTextureName(){
        return this.textureName;
    }

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    public String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    /**
     * 获取模型 {@link Identifier}
     * @return 模型 Identifier
     */
    public Identifier getModelId(){
        return IdUtil.get(this.getPrefixedPath() + this.getTextureName());
    }

    /**
     * 方块可以改色
     * @return 返回 false, 不可以改色
     */
    public boolean canSprayBlock(){
        return false;
    }

    /**
     * 方块正在被改色, 在此处被调用 {@link fengliu.paintwar.paintwar.item.tool.Brush#sprayBlock(World, BlockPos, BlockState, DyeColor) sprayBlock}
     * @param world 世界
     * @param pos 方块坐标
     * @param blockState 方块状态
     * @param color 会被改为的颜色
     * @param sprayBlock 是否可以改色
     * @return 返回新的方块状态. 如果返回旧方块状态将继续改色
     */
    public BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock){
        return blockState;
    }

    /**
     * 生成方块状态, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateBlockStateModels(BlockStateModelGenerator) generateBlockStateModels}
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator){

    }

    /**
     * 生成方块模型, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateBlockStateModels(BlockStateModelGenerator) generateBlockStateModels}
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        if (this.color == null){
            return;
        }
        // 指向父级模型
        Identifier modelId = IdUtil.get(this.getPrefixedPath() + this.getTextureName());
        new Model(Optional.of(modelId), Optional.empty())
                .upload(this, TextureMap.all(modelId), blockStateModelGenerator.modelCollector);
    }
}
