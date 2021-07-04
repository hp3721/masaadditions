package com.red.masaadditions.minihud_additions.minihud_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.minihud_additions.config.ConfigsExtended;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.minihud.config.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

@Pseudo
@Mixin(value = Configs.Colors.class, remap = false)
public class MixinColorConfigs {
    @Final
    @Shadow
    public static ImmutableList<IConfigBase> OPTIONS = new ImmutableList.Builder<IConfigBase>().addAll(Configs.Colors.OPTIONS).addAll(ConfigsExtended.Colors.ADDITIONAL_OPTIONS).build();
}
