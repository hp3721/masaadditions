package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.entity.passive.LlamaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaEntity.class)
public class MixinLlamaEntity {
    @Inject(method = "canBeControlledByRider", at = @At("HEAD"), cancellable = true)
    private void allowLamaSteering(CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggleExtended.TWEAK_LLAMA_STEERING.getBooleanValue()) {
            cir.setReturnValue(true);
        }
    }
}
