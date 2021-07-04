package com.red.masaadditions.minihud_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigColor;

public class ConfigsExtended {
    public static class Colors {
        public static final ConfigColor BEACON_RANGE_LVL1_OVERLAY_COLOR = new ConfigColor("beaconRangeLvl1", "#20E060FF", "Color for level 1 beacon overlay.");
        public static final ConfigColor BEACON_RANGE_LVL2_OVERLAY_COLOR = new ConfigColor("beaconRangeLvl2", "#20FFB040", "Color for level 2 beacon overlay.");
        public static final ConfigColor BEACON_RANGE_LVL3_OVERLAY_COLOR = new ConfigColor("beaconRangeLvl3", "#20FFF040", "Color for level 3 beacon overlay.");
        public static final ConfigColor BEACON_RANGE_LVL4_OVERLAY_COLOR = new ConfigColor("beaconRangeLvl4", "#2060FF40", "Color for level 4 beacon overlay.");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                BEACON_RANGE_LVL1_OVERLAY_COLOR,
                BEACON_RANGE_LVL2_OVERLAY_COLOR,
                BEACON_RANGE_LVL3_OVERLAY_COLOR,
                BEACON_RANGE_LVL4_OVERLAY_COLOR
        );
    }
}