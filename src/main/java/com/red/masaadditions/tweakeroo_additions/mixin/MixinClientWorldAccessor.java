package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientWorld.class)
public interface MixinClientWorldAccessor {
    @Accessor("pendingUpdateManager")
    PendingUpdateManager tweakermore_getPendingUpdateManager();
}
