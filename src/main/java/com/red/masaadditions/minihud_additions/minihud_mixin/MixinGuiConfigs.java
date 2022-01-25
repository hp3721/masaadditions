package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import fi.dy.masa.malilib.config.*;
import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.config.RendererToggle;
import fi.dy.masa.minihud.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Pseudo
@Mixin(value = GuiConfigs.class, remap = false)
public class MixinGuiConfigs {
    private static final ImmutableList<IHotkeyTogglable> RENDERER_TOGGLE = new ImmutableList.Builder<IHotkeyTogglable>().addAll(Arrays.asList(RendererToggle.values())).addAll(Arrays.asList(RendererToggleExtended.values())).build();

    @ModifyArg(method = "getConfigs", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/GuiConfigsBase$ConfigOptionWrapper;createFor(Ljava/util/Collection;)Ljava/util/List;", ordinal = 4), index = 0)
    private Collection<? extends IConfigBase> ExtendRendererHotkeys(Collection<? extends IConfigBase> configs) {
        List<IConfigBase> list = new ArrayList<>();
        list.add(Configs.Generic.MAIN_RENDERING_TOGGLE);
        list.addAll(ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, RENDERER_TOGGLE));
        return list;
    }
}
