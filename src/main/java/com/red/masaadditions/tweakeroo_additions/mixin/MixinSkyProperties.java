package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.render.SkyProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SkyProperties.Overworld.class)
public class MixinSkyProperties {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/SkyProperties;<init>(FZLnet/minecraft/client/render/SkyProperties$SkyType;ZZ)V"), index = 0)
    private static float Overworld(float cloudsHeight) {
        return (float) ConfigsExtended.Generic.CLOUD_HEIGHT.getDoubleValue();
    }
}