package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface MixinAbstractBlockAccessor {
    @Accessor("velocityMultiplier")
    void setVelocityMultiplier(float velocityMultiplier);

    @Accessor("jumpVelocityMultiplier")
    void setJumpVelocityMultiplier(float jumpVelocityMultiplier);
}
