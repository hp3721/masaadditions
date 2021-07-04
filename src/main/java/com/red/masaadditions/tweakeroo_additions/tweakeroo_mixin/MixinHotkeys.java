package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.tweakeroo.config.Hotkeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Pseudo
@Mixin(value = Hotkeys.class, remap = false)
public class MixinHotkeys {
    @Final
    @Shadow
    public static List<ConfigHotkey> HOTKEY_LIST = new ImmutableList.Builder<ConfigHotkey>().addAll(Hotkeys.HOTKEY_LIST).addAll(HotkeysExtended.EXTENDED_HOTKEY_LIST).build();
}