package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import fi.dy.masa.minihud.renderer.OverlayRendererBase;
import fi.dy.masa.minihud.renderer.RenderContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = RenderContainer.class, remap = false)
public abstract class MixinRenderContainer {
    @Shadow
    protected abstract void addRenderer(OverlayRendererBase renderer);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void RenderContainer(CallbackInfo ci) {
        this.addRenderer(new OverlayRendererBeaconRange());
    }
}
