
package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import fi.dy.masa.minihud.renderer.OverlayRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = OverlayRenderer.class, remap = false)
public class MixinOverlayRenderer {
    @Inject(method = "renderOverlays", at = @At("RETURN"))
    private static void renderOverlays(MatrixStack matrixStack, Matrix4f projMatrix, MinecraftClient mc, CallbackInfo ci) {
        if (RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue() && mc.player != null) {
            OverlayRendererBeaconRange.renderBeaconBoxForPlayerIfHoldingItem(mc.player);
        }
    }
}