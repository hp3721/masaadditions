package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.event.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Pseudo
@Mixin(value = InputHandler.class, remap = false)
public class MixinInputHandler {
    private static final ImmutableList<IHotkeyTogglable> FEATURE_TOGGLE = new ImmutableList.Builder<IHotkeyTogglable>().addAll(Arrays.asList(FeatureToggle.values())).addAll(Arrays.asList(FeatureToggleExtended.values())).build();

    @Inject(method = "addKeysToMap", at = @At("RETURN"))
    private void addKeysToMap(IKeybindManager manager, CallbackInfo ci) {
        for (FeatureToggleExtended toggle : FeatureToggleExtended.values()) {
            manager.addKeybindToMap(toggle.getKeybind());
        }

        for (IHotkey hotkey : HotkeysExtended.EXTENDED_HOTKEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }

        for (IHotkey hotkey : ConfigsExtended.Disable.ADDITIONAL_OPTIONS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @ModifyArg(method = "addHotkeys", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/hotkeys/IKeybindManager;addHotkeysForCategory(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 0), index = 2)
    private List<? extends IHotkeyTogglable> addDisableHotkeys(List<? extends IHotkeyTogglable> hotkeys) {
        return ConfigsExtended.Disable.ADDITIONAL_OPTIONS;
    }

    @ModifyArg(method = "addHotkeys", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/hotkeys/IKeybindManager;addHotkeysForCategory(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 2)
    private List<? extends ConfigHotkey> addGenericHotkeys(List<? extends ConfigHotkey> hotkeys) {
        return HotkeysExtended.EXTENDED_HOTKEY_LIST;
    }

    @ModifyArg(method = "addHotkeys", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/hotkeys/IKeybindManager;addHotkeysForCategory(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 2), index = 2)
    private List<? extends IHotkeyTogglable> addFeatureHotkeys(List<? extends IHotkeyTogglable> hotkeys) {
        return FEATURE_TOGGLE;
    }
}
