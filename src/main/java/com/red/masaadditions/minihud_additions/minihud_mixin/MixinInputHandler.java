package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.minihud.config.RendererToggle;
import fi.dy.masa.minihud.event.InputHandler;
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
    private static final ImmutableList<IHotkeyTogglable> RENDERER_TOGGLE = new ImmutableList.Builder<IHotkeyTogglable>().addAll(Arrays.asList(RendererToggle.values())).addAll(Arrays.asList(RendererToggleExtended.values())).build();

    @Inject(method = "addKeysToMap", at = @At("RETURN"))
    private void addKeysToMap(IKeybindManager manager, CallbackInfo ci) {
        for (RendererToggleExtended toggle : RendererToggleExtended.values()) {
            manager.addKeybindToMap(toggle.getKeybind());
        }
    }

    @ModifyArg(method = "addHotkeys", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/hotkeys/IKeybindManager;addHotkeysForCategory(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 2), index = 2)
    private List<? extends IHotkeyTogglable> addHotkeysForCategory(List<? extends IHotkeyTogglable> hotkeys) {
        return RENDERER_TOGGLE;
    }
}
