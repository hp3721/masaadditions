package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    // From UsefulMod by nessie
    @Inject(method = "onCombatEvent", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
    private void onPlayerDeath(CombatEventS2CPacket packetIn, CallbackInfo ci) {
        // ClientPlayerEntity::showDeathScreen will prevent tweakPrintDeathCoordinates from working
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (FeatureToggleExtended.TWEAK_RESPAWN_ON_DEATH.getBooleanValue() && player != null) {
            player.requestRespawn();
        }
    }
}
