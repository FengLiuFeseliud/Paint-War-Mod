package fengliu.paintwar.paintwar.util.block;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public interface IModBlock {
    String PREFIXED_PATH = "block/";

    String getBlockName();

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    String getTextureName();

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    default String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    /**
     * 获取模型 {@link Identifier}
     * @return 模型 Identifier
     */
    default Identifier getModelId(){
        return IdUtil.get(this.getPrefixedPath() + this.getTextureName());
    }

    /**
     * 方块可以改色
     * @return 返回 false, 不可以改色
     */
    default boolean canSprayBlock(){
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
    default BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock){
        return blockState;
    }

    /**
     * 生成方块状态, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateBlockStateModels(BlockStateModelGenerator) generateBlockStateModels}
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    default void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator){
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create((Block) this,
                        BlockStateVariant.create().put(VariantSettings.MODEL, this.getModelId())));
    }

    /**
     * 生成方块模型, 参考 {@link fengliu.paintwar.paintwar.data.generation.ModelsDataGeneration#generateBlockStateModels(BlockStateModelGenerator) generateBlockStateModels}
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    default void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        if (!(this instanceof IColor)){
            return;
        }

        // 指向父级模型
        Identifier modelId = IdUtil.get(this.getPrefixedPath() + this.getTextureName());
        new Model(Optional.of(modelId), Optional.empty())
                .upload((Block) this, TextureMap.all(modelId), blockStateModelGenerator.modelCollector);
    }
}
