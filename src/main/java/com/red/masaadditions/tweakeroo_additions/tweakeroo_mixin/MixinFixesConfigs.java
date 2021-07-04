package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.tweakeroo.config.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

@Pseudo
@Mixin(value = Configs.Fixes.class, remap = false)
public class MixinFixesConfigs {
    @Final
    @Shadow
    public static ImmutableList<IConfigBase> OPTIONS = new ImmutableList.Builder<IConfigBase>().addAll(Configs.Fixes.OPTIONS).addAll(ConfigsExtended.Fixes.ADDITIONAL_OPTIONS).build();
}
