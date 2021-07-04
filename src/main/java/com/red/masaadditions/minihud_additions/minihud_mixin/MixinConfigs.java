package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.config.RendererToggle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;
import java.util.List;

@Pseudo
@Mixin(value = Configs.class, remap = false)
public class MixinConfigs {
    private static final ImmutableList<IHotkeyTogglable> RENDERER_OPTIONS = new ImmutableList.Builder<IHotkeyTogglable>().addAll(Arrays.asList(RendererToggle.values())).addAll(Arrays.asList(RendererToggleExtended.values())).build();

    @ModifyArg(method = "loadFromFile", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/ConfigUtils;readHotkeyToggleOptions(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 3)
    private static List<? extends IHotkeyTogglable> readRendererOptions(List<? extends IHotkeyTogglable> options) {
        return RENDERER_OPTIONS;
    }

    @ModifyArg(method = "saveToFile", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/ConfigUtils;writeHotkeyToggleOptions(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 3)
    private static List<? extends IHotkeyTogglable> writeRendererOptions(List<? extends IHotkeyTogglable> options) {
        return RENDERER_OPTIONS;
    }
}
