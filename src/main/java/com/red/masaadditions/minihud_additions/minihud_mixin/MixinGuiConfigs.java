package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.minihud.config.RendererToggle;
import fi.dy.masa.minihud.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Arrays;
import java.util.List;

@Pseudo
@Mixin(value = GuiConfigs.class, remap = false)
public class MixinGuiConfigs {
    private static final ImmutableList<IConfigValue> RENDERER_TOGGLE = new ImmutableList.Builder<IConfigValue>().addAll(Arrays.asList(RendererToggle.values())).addAll(Arrays.asList(RendererToggleExtended.values())).build();

    @ModifyVariable(method = "getConfigs", at = @At(value = "STORE", target = "Lfi/dy/masa/minihud/gui/GuiConfigs;getConfigs()Ljava/util/List;", ordinal = 5))
    private List<? extends IConfigBase> ExtendRendererHotkeys(List<? extends IConfigBase> configs) {
        return ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, RENDERER_TOGGLE);
    }
}
