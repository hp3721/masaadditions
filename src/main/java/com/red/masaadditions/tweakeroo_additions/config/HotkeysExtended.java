package com.red.masaadditions.tweakeroo_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class HotkeysExtended {
    public static final ConfigHotkey BLINK_DRIVE = new ConfigHotkey("blinkDrive", "", "Teleports the player (using a tp command) to the location the player is looking at.\nPorted from 1.12 Tweakeroo.");
    public static final ConfigHotkey BLINK_DRIVE_Y_LEVEL = new ConfigHotkey("blinkDriveYLevel", "", "Teleports the player (using a tp command)\nto the location  the player is looking at,\nbut it maintains the current y-position.\nPorted from 1.12 Tweakeroo.");
    public static final ConfigHotkey SNAP_AIM_FLIP_HORIZONTALLY = new ConfigHotkey("snapAimFlipHorizontally", "", "Flips snap aim horizontally.");
    public static final ConfigHotkey SNAP_AIM_FLIP_VERTICALLY = new ConfigHotkey("snapAimFlipVertically", "", "Flips snap aim vertically.");

    public static final List<ConfigHotkey> EXTENDED_HOTKEY_LIST = ImmutableList.of(
            BLINK_DRIVE,
            BLINK_DRIVE_Y_LEVEL,
            SNAP_AIM_FLIP_HORIZONTALLY,
            SNAP_AIM_FLIP_VERTICALLY
    );
}
