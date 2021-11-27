package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Inject(method = "getBlockParticle", at = @At("HEAD"), cancellable = true)
    private void getBlockParticle(CallbackInfoReturnable<Block> cir) {
        if (FeatureToggleExtended.TWEAK_ALWAYS_RENDER_BARRIER_PARTICLES.getBooleanValue()) {
            cir.setReturnValue(Blocks.BARRIER);
        }
    }
}
