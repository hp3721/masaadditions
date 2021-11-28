package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface MixinAbstractBlockAccessor {
    @Mutable
    @Accessor("velocityMultiplier")
    void setVelocityMultiplier(float velocityMultiplier);

    @Mutable
    @Accessor("jumpVelocityMultiplier")
    void setJumpVelocityMultiplier(float jumpVelocityMultiplier);
}
