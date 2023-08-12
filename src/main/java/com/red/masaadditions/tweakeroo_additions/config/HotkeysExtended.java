package com.red.masaadditions.tweakeroo_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

import java.util.List;

public class HotkeysExtended {
    public static final ConfigHotkey BLINK_DRIVE = new ConfigHotkey("blinkDrive", "", "Teleports the player (using a tp command) to the location the player is looking at.\nPorted from 1.12 Tweakeroo.");
    public static final ConfigHotkey BLINK_DRIVE_Y_LEVEL = new ConfigHotkey("blinkDriveYLevel", "", "Teleports the player (using a tp command)\nto the location  the player is looking at,\nbut it maintains the current y-position.\nPorted from 1.12 Tweakeroo.");
    public static final ConfigHotkey REPLACEMENT_MODE = new ConfigHotkey("replacementMode", "", KeybindSettings.MODIFIER_INGAME, "While block placing, replaces the block you're looking at instead of placing against it.\nOnly works if you are opped or in singleplayer.");

    public static final List<ConfigHotkey> EXTENDED_HOTKEY_LIST = ImmutableList.of(
            BLINK_DRIVE,
            BLINK_DRIVE_Y_LEVEL,
            REPLACEMENT_MODE
    );
}
