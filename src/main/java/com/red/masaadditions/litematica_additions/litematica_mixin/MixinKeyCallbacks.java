package com.red.masaadditions.litematica_additions.litematica_mixin;

import com.red.masaadditions.litematica_additions.config.ConfigsExtended;
import com.red.masaadditions.litematica_additions.config.HotkeysExtended;
import fi.dy.masa.litematica.event.KeyCallbacks;
import fi.dy.masa.litematica.util.SchematicWorldRefresher;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = KeyCallbacks.class, remap = false)
public class MixinKeyCallbacks {
    @Inject(method = "init", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        HotkeysExtended.RENDER_HELD_ITEM_ONLY_TOGGLE.getKeybind().setCallback(new KeyCallbackToggleBooleanConfigWithMessage(ConfigsExtended.Generic.RENDER_HELD_ITEM_ONLY));
        ConfigsExtended.Generic.RENDER_HELD_ITEM_ONLY.setValueChangeCallback((config) -> SchematicWorldRefresher.INSTANCE.updateAll());
    }
}
