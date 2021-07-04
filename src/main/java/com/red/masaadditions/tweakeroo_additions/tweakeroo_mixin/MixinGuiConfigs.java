package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Arrays;
import java.util.List;

@Pseudo
@Mixin(value = GuiConfigs.class, remap = false)
public class MixinGuiConfigs {
    private static final ImmutableList<IConfigValue> FEATURE_TOGGLES = new ImmutableList.Builder<IConfigValue>().addAll(Arrays.asList(FeatureToggle.values())).addAll(Arrays.asList(FeatureToggleExtended.values())).build();

    @ModifyVariable(method = "getConfigs", at = @At(value = "STORE", target = "Lfi/dy/masa/tweakeroo/gui/GuiConfigs;getConfigs()Ljava/util/List;", ordinal = 5))
    private List<? extends IConfigBase> ExtendFeatureToggles(List<? extends IConfigBase> configs) {
        return ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, FEATURE_TOGGLES);
    }

    @ModifyVariable(method = "getConfigs", at = @At(value = "STORE", target = "Lfi/dy/masa/tweakeroo/gui/GuiConfigs;getConfigs()Ljava/util/List;", ordinal = 6))
    private List<? extends IConfigBase> ExtendFeatureHotkeys(List<? extends IConfigBase> configs) {
        return ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, FEATURE_TOGGLES);
    }
}
