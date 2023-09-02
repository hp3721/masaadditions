package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended.Lists;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.InventoryTweaks;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.tweakeroo.config.Configs;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Pseudo
@Mixin(value = Configs.class, remap = false)
public class MixinConfigs {
    private static final ImmutableList<IHotkeyTogglable> FEATURE_OPTIONS = new ImmutableList.Builder<IHotkeyTogglable>().addAll(Arrays.asList(FeatureToggle.values())).addAll(Arrays.asList(FeatureToggleExtended.values())).build();

    @ModifyArg(method = "loadFromFile", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/ConfigUtils;readHotkeyToggleOptions(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 3)
    private static List<? extends IHotkeyTogglable> readFeatureOptions(List<? extends IHotkeyTogglable> options) {
        return FEATURE_OPTIONS;
    }

    @ModifyArg(method = "saveToFile", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/ConfigUtils;writeHotkeyToggleOptions(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 3)
    private static List<? extends IHotkeyTogglable> writeFeatureOptions(List<? extends IHotkeyTogglable> options) {
        return FEATURE_OPTIONS;
    }

    @Inject(method = "loadFromFile", at = @At("RETURN"))
    private static void setPerimeterOutlineBlocks(CallbackInfo ci) {
        PlacementTweaks.setPerimeterOutlineBlocks(Lists.PERIMETER_OUTLINE_BLOCKS_LIST.getStrings());
        InventoryTweaks.setSwapAlmostBrokenToolsWhitelist(Lists.SWAP_ALMOST_BROKEN_TOOLS_WHITELIST.getStrings());
        InventoryTweaks.setSwapAlmostBrokenToolsBlacklist(Lists.SWAP_ALMOST_BROKEN_TOOLS_BLACKLIST.getStrings());
    }
}
