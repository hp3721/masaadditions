package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.client.network.PendingUpdateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PendingUpdateManager.PendingUpdate.class)
public interface MixinPendingUpdateAccessor {
    @Accessor
    int getSequence();
}
