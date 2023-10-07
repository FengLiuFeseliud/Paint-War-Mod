package fengliu.paintwar.paintwar.item.tool;

import fengliu.paintwar.paintwar.entity.thrown.WallShellEntity;
import fengliu.paintwar.paintwar.item.ModItems;
import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.color.IColor;
import fengliu.paintwar.paintwar.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED_THREE_LAYERS;

public class WallGun extends EmptyWallGun implements IColor {
    private final DyeColor dyeColor;

    public WallGun(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        GENERATED_THREE_LAYERS.upload(
                ModelIds.getItemModelId(this),
                TextureMap.layered(
                        IdUtil.get(this.getTextureName()+"/"+this.getTextureName()).withPrefixedPath(this.getPrefixedPath()),
                        IdUtil.get(this.getTextureName()+"/"+this.getTextureName()).withPrefixedPath(this.getPrefixedPath()),
                        IdUtil.get(this.getTextureName()+"/"+this.getTextureName()+"_overlay").withPrefixedPath(this.getPrefixedPath())
                ),
                itemModelGenerator.writer
        );
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, this, 1)
                .input(ModItems.EMPTY_WALL_GUN)
                .input(DyeItem.byColor(this.getColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);

        for(Item item: ModItems.WALL_GUNS){
            ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, this, 1)
                    .input(item)
                    .input(DyeItem.byColor(this.getColor()))
                    .criterion(FabricRecipeProvider.hasItem(this),
                            FabricRecipeProvider.conditionsFromItem(this))
                    .offerTo(exporter);
        }
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
    }

    public static WallShellEntity getColorShellEntity(World world, PlayerEntity player, List<Block> wallBlocks, @Nullable DyeColor dyeColor){
        WallShellEntity wallShell = new WallShellEntity(player, world, wallBlocks);
        if (dyeColor == null){
            wallShell.setItem(ModItems.WALL_GUNS.get(0).getDefaultStack());
            return wallShell;
        }

        for(BaseItem item: ModItems.WALL_SHELLS){
            if (!((IColor) item).getColor().equals(dyeColor)){
                continue;
            }

            wallShell.setItem(item.getDefaultStack());
        }
        return wallShell;
    }

    @Override
    public WallShellEntity getShellEntity(World world, PlayerEntity player, List<Block> wallBlocks){
        return WallGun.getColorShellEntity(world, player, wallBlocks, this.getColor());
    }
}
