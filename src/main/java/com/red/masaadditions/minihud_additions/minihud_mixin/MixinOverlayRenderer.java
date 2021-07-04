package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import fi.dy.masa.minihud.renderer.OverlayRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = OverlayRenderer.class, remap = false)
public class MixinOverlayRenderer {
    @Inject(method = "renderOverlays", at = @At("RETURN"))
    private static void renderOverlays(MatrixStack matrixStack, MinecraftClient mc, float partialTicks, CallbackInfo ci) {
        if (RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue()) {
            double dx = mc.player.prevX + (mc.player.getPos().x - mc.player.prevX) * partialTicks;
            double dy = mc.player.prevY + (mc.player.getPos().y - mc.player.prevY) * partialTicks;
            double dz = mc.player.prevZ + (mc.player.getPos().z - mc.player.prevZ) * partialTicks;
            OverlayRendererBeaconRange.renderBeaconBoxForPlayerIfHoldingItem(mc.player, dx, dy, dz, partialTicks);
        }
    }
}
