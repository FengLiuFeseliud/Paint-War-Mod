package fengliu.paintwar.paintwar.util.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class FacingBlock extends BaseBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public FacingBlock(Settings settings, String name) {
        super(settings, name);
    }

    public FacingBlock(AbstractBlock.Settings settings, DyeColor color, String name) {
        super(settings, color, name);
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier modelId = this.getModelId();
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(this)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                        .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
                )
        );
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
