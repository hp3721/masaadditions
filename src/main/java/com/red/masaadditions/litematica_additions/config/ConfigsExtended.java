package com.red.masaadditions.litematica_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;

public class ConfigsExtended {
    public static class Generic {
        public static final ConfigBoolean MATERIAL_LIST_IGNORE_BLOCK_STATE = new ConfigBoolean("materialListIgnoreBlockState", false, "Ignores block state for material list counts", "Material List Ignore Block State");
        public static final ConfigBoolean RENDER_HELD_ITEM_ONLY = new ConfigBoolean("renderHeldItemOnly", false, "Only renders currently held block item", "Render Held Item Only");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                MATERIAL_LIST_IGNORE_BLOCK_STATE,
                RENDER_HELD_ITEM_ONLY
        );
    }
}