package fengliu.paintwar.paintwar.item.block;

import fengliu.paintwar.paintwar.util.IdUtil;
import fengliu.paintwar.paintwar.util.block.IModBlock;
import fengliu.paintwar.paintwar.util.item.BaseTallBlockItem;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;

import static net.minecraft.data.client.Models.GENERATED;

public class NoColorDoor extends BaseTallBlockItem {
    public <B extends Block & IModBlock> NoColorDoor(B block) {
        super(block);
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        GENERATED.upload(ModelIds.getItemModelId(this), TextureMap.layer0(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath())), itemModelGenerator.writer);
    }
}
