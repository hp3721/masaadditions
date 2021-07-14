package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.tweakeroo.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = InitializationHandler.class, remap = false)
public class MixinInitializationHandler {
    @Inject(method = "onGameInitDone", at = @At("RETURN"))
    private void onGameInitDone(CallbackInfo ci) {
        // Hack fix to get around 'Mod Configs only loaded after mod initialisation' https://github.com/maruohon/malilib/issues/25
        Configs.Disable.DISABLE_SLIME_BLOCK_SLOWDOWN.onValueChanged();
        ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_SLOWDOWN.onValueChanged();
        ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_JUMPING.onValueChanged();
    }
}
