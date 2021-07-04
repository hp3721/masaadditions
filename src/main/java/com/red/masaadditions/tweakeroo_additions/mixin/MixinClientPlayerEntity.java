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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {
    @Shadow
    public int ticksSinceSprintingChanged;

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(boolean sprinting, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_SWIMMING.getBooleanValue() && ConfigsExtended.Disable.DISABLE_SPRINTING_UNDERWATER.getBooleanValue()) {
            super.setSprinting(false);
            this.ticksSinceSprintingChanged = 0;
            ci.cancel();
        }
    }
}