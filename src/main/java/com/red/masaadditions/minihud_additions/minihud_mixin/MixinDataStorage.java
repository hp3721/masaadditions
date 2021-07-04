package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import fi.dy.masa.minihud.util.DataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = DataStorage.class, remap = false)
public class MixinDataStorage {
    @Inject(method = "reset", at = @At("RETURN"))
    private void reset(boolean isLogout, CallbackInfo ci) {
        OverlayRendererBeaconRange.clear();
    }

    @Inject(method = "onWorldJoin", at = @At("RETURN"))
    private void onWorldJoin(CallbackInfo ci) {
        OverlayRendererBeaconRange.setNeedsUpdate();
    }
}
