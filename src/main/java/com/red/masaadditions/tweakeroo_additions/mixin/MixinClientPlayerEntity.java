package com.red.masaadditions.tweakeroo_additions.mixin;

import com.mojang.authlib.GameProfile;
import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {
    @Shadow
    public abstract boolean isSubmergedInWater();

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "canStartSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExtended.Disable.DISABLE_SPRINTING_UNDERWATER.getBooleanValue() && ((this.isTouchingWater() && !this.isSubmergedInWater()) || (ConfigsExtended.Disable.DISABLE_SWIMMING.getBooleanValue()))) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "shouldSpawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void shouldSpawnSprintingParticles(CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExtended.Disable.DISABLE_FOOTSTEP_PARTICLES.getBooleanValue())
            cir.setReturnValue(false);
    }
}