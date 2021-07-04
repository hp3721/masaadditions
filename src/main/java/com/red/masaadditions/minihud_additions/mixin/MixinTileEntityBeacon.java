package com.red.masaadditions.minihud_additions.mixin;

import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import net.minecraft.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// From 1.12 MiniHUD by Masa
@Mixin(BeaconBlockEntity.class)
public abstract class MixinTileEntityBeacon {
    @Shadow
    private int level;

    private int levelsPre;

    @Inject(method = "updateLevel", at = @At("HEAD"))
    private void onUpdateSegmentsPre(int x, int y, int z, CallbackInfo ci) {
        this.levelsPre = this.level;
    }

    @Inject(method = "updateLevel", at = @At("RETURN"))
    private void onUpdateSegmentsPost(int x, int y, int z, CallbackInfo ci) {
        if (this.level != this.levelsPre) {
            OverlayRendererBeaconRange.setNeedsUpdate();
        }
    }
}
