package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DimensionEffects.Overworld.class)
public class MixinDimensionEffects {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DimensionEffects;<init>(FZLnet/minecraft/client/render/DimensionEffects$SkyType;ZZ)V"), index = 0)
    private static float Overworld(float cloudsHeight) {
        return (float) ConfigsExtended.Generic.CLOUD_HEIGHT.getDoubleValue();
    }
}