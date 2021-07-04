package com.red.masaadditions.litematica_additions.util;

import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import net.minecraft.util.Util;

public class MiscUtils {
    public static class ButtonListenerOpenFolder implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            Util.getOperatingSystem().open(DataManager.getSchematicsBaseDirectory());
        }
    }
}
