package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemPlacementContext.class)
public interface MixinItemPlacementContextAccessor {
    @Accessor
    void setCanReplaceExisting(boolean canReplaceExisting);
}
