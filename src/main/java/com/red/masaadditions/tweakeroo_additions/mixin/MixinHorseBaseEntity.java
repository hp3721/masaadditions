package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HorseBaseEntity.class)
public class MixinHorseBaseEntity {
    @Redirect(method = "travel", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/HorseBaseEntity;isSaddled()Z"))
    public boolean spoofIsSaddled(HorseBaseEntity entity) {
        if (FeatureToggleExtended.TWEAK_LLAMA_STEERING.getBooleanValue() && (Object) this instanceof LlamaEntity && ((LlamaEntity) (Object) this).getCarpetColor() != null) // The only way to know on the client that the Llama has a Carpet
        {
            return true;
        }

        return entity.isSaddled();
    }
}
