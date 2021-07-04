package com.red.masaadditions.litematica_additions.util;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.GuiUtils;

import java.nio.file.Path;

public class FileImportUtil {
    public static void saveDraggedLitematic(Path path) {
        LitematicaSchematic schematic = LitematicaSchematic.createFromFile(path.getParent().toFile(), path.getFileName().toString());

        if (schematic == null) {
            return;
        }

        GuiFileImport gui = new GuiFileImport(schematic);
        gui.setParent(GuiUtils.getCurrentScreen());
        GuiBase.openGui(gui);
    }
}
