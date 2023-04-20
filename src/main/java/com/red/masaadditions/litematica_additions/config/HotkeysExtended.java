package com.red.masaadditions.litematica_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class HotkeysExtended {
    public static final ConfigHotkey RENDER_HELD_ITEM_ONLY_TOGGLE = new ConfigHotkey("renderHeldItemOnlyToggle", "", "Toggles renderHeldItemOnly on/off");

    public static final List<ConfigHotkey> EXTENDED_HOTKEY_LIST = ImmutableList.of(
            RENDER_HELD_ITEM_ONLY_TOGGLE
    );
}
