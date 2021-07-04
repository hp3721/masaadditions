package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import com.red.masaadditions.tweakeroo_additions.util.KeyCallbackHotkeysGeneric;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.tweakeroo.config.Callbacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = Callbacks.class, remap = false)
public abstract class MixinCallbacks {
    @Inject(method = "init", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        final IHotkeyCallback callbackGeneric = new KeyCallbackHotkeysGeneric();
        HotkeysExtended.BLINK_DRIVE.getKeybind().setCallback(callbackGeneric);
        HotkeysExtended.BLINK_DRIVE_Y_LEVEL.getKeybind().setCallback(callbackGeneric);

        ConfigsExtended.Lists.PERIMETER_OUTLINE_BLOCKS_LIST.setValueChangeCallback((cfg) -> PlacementTweaks.setPerimeterOutlineBlocks(cfg.getStrings()));
    }
}
