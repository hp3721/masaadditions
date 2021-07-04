package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.red.masaadditions.minihud_additions.config.ConfigsExtended;
import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import fi.dy.masa.minihud.hotkeys.KeyCallbacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = KeyCallbacks.class, remap = false)
public class MixinKeyCallbacks {
    @Inject(method = "init", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        ConfigsExtended.Colors.BEACON_RANGE_LVL1_OVERLAY_COLOR.setValueChangeCallback((config) -> OverlayRendererBeaconRange.setNeedsUpdate());
        ConfigsExtended.Colors.BEACON_RANGE_LVL2_OVERLAY_COLOR.setValueChangeCallback((config) -> OverlayRendererBeaconRange.setNeedsUpdate());
        ConfigsExtended.Colors.BEACON_RANGE_LVL3_OVERLAY_COLOR.setValueChangeCallback((config) -> OverlayRendererBeaconRange.setNeedsUpdate());
        ConfigsExtended.Colors.BEACON_RANGE_LVL4_OVERLAY_COLOR.setValueChangeCallback((config) -> OverlayRendererBeaconRange.setNeedsUpdate());

        RendererToggleExtended.OVERLAY_BEACON_RANGE.setValueChangeCallback((config) -> OverlayRendererBeaconRange.setNeedsUpdate());
    }
}
