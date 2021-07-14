package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import com.red.masaadditions.tweakeroo_additions.util.Callbacks;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = fi.dy.masa.tweakeroo.config.Callbacks.class, remap = false)
public abstract class MixinCallbacks {
    @Inject(method = "init", at = @At("RETURN"))
    private static void init(MinecraftClient mc, CallbackInfo ci) {
        final IHotkeyCallback callbackGeneric = new Callbacks.KeyCallbackHotkeysGeneric();
        HotkeysExtended.BLINK_DRIVE.getKeybind().setCallback(callbackGeneric);
        HotkeysExtended.BLINK_DRIVE_Y_LEVEL.getKeybind().setCallback(callbackGeneric);
        ConfigsExtended.Lists.PERIMETER_OUTLINE_BLOCKS_LIST.setValueChangeCallback((cfg) -> PlacementTweaks.setPerimeterOutlineBlocks(cfg.getStrings()));
        ConfigsExtended.Disable.DISABLE_PLANT_BLOCK_MODEL_OFFSET.setValueChangeCallback((cfg) -> mc.worldRenderer.reload());
        FeatureToggleExtended.TWEAK_OVERRIDE_WINDOW_TITLE.setValueChangeCallback((cfg) -> mc.updateWindowTitle());
        ConfigsExtended.Generic.WINDOW_TITLE_OVERRIDE.setValueChangeCallback((cfg) -> mc.updateWindowTitle());
        ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_SLOWDOWN.setValueChangeCallback(new Callbacks.FeatureCallbackHoney(ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_SLOWDOWN));
        ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_JUMPING.setValueChangeCallback(new Callbacks.FeatureCallbackHoney(ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_JUMPING));
        ConfigsExtended.Disable.DISABLE_FARMLAND_MAKING.setValueChangeCallback((cfg) -> FeatureToggle.TWEAK_FAST_RIGHT_CLICK.setBooleanValue(false));
        FeatureToggle.TWEAK_FAST_RIGHT_CLICK.setValueChangeCallback((cfg) -> {
            if (ConfigsExtended.Disable.DISABLE_FARMLAND_MAKING.getBooleanValue()) {
                FeatureToggle.TWEAK_FAST_RIGHT_CLICK.setBooleanValue(false);
            }
        });
        FeatureToggle.TWEAK_FAST_RIGHT_CLICK.getKeybind().setCallback(new Callbacks.KeyCallbackToggleFastRightClick(FeatureToggle.TWEAK_FAST_RIGHT_CLICK));
    }
}
