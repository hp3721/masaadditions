package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void onProcessKeybindsPre(CallbackInfo ci)
    {
        if (((MinecraftClient) (Object) this).currentScreen == null)
        {
            if (FeatureToggleExtended.TWEAK_MOVEMENT_HOLD.getBooleanValue())
            {
                for (KeyBinding movementKey : MiscUtils.MOVEMENT_HOLD_KEYS) {
                    movementKey.setPressed(true);
                }
            }
        }
    }

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir) {
        if (FeatureToggleExtended.TWEAK_OVERRIDE_WINDOW_TITLE.getBooleanValue()) {
            cir.setReturnValue(ConfigsExtended.Generic.WINDOW_TITLE_OVERRIDE.getStringValue());
        }
    }
}