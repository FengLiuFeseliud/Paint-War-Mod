package fengliu.paintwar.paintwar.block;

import fengliu.paintwar.paintwar.data.ModModels;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.color.IColor;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class NoColorDoorBlock extends DoorBlock implements IModBlock {
    private final String blockName;
    private final String textureName;

    public NoColorDoorBlock(Settings settings, BlockSetType blockSetType, String name) {
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
    public BlockState onSprayBlock(World world, BlockPos pos, BlockState blockState, DyeColor color, boolean sprayBlock) {
        return IColor.getColor(ModBlocks.COLOR_DOOR_BLOCKS, color).getDefaultState();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isSneaking()){
            return ActionResult.PASS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        ModModels.registerColorDoor(this, this.getModelId(), blockStateModelGenerator);
    }
}
