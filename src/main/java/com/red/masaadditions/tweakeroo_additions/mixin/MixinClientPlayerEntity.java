package com.red.masaadditions.tweakeroo_additions.mixin;

import com.mojang.authlib.GameProfile;
import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.encryption.PlayerPublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {
    @Shadow
    public int ticksSinceSprintingChanged;

    @Shadow
    public abstract boolean isSubmergedInWater();

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile, @Nullable PlayerPublicKey publicKey) {
        super(world, profile, publicKey);
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(boolean sprinting, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_SPRINTING_UNDERWATER.getBooleanValue() && ((this.isTouchingWater() && !this.isSubmergedInWater()) || (ConfigsExtended.Disable.DISABLE_SWIMMING.getBooleanValue()))) {
            super.setSprinting(false);
            this.ticksSinceSprintingChanged = 0;
            ci.cancel();
        }
    }

    @Inject(method = "shouldSpawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void shouldSpawnSprintingParticles(CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExtended.Disable.DISABLE_FOOTSTEP_PARTICLES.getBooleanValue())
            cir.setReturnValue(false);
    }
}