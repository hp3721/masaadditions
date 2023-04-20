package com.red.masaadditions.litematica_additions.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FlowerPotBlock.class)
public interface MixinFlowerPotBlockAccessor {
    @Accessor("CONTENT_TO_POTTED")
    static Map<Block, Block> getContentToPotted() {
        throw new AssertionError();
    }

    @Accessor("content")
    Block getContent();
}
