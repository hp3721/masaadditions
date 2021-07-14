package com.red.masaadditions.litematica_additions.util;

import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.gui.GuiSchematicSaveBase;
import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.interfaces.ICompletionListener;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;

import java.io.File;

public class GuiFileImport extends GuiFileImportBase implements ICompletionListener {
    public GuiFileImport(LitematicaSchematic schematic) {
        super(schematic);

        this.title = StringUtils.translate("masaadditions.gui.title.save_dragged_schematic");
    }

    @Override
    public String getBrowserContext() {
        return "schematic_save";
    }

    @Override
    public File getDefaultDirectory() {
        return DataManager.getSchematicsBaseDirectory();
    }

    @Override
    protected IButtonActionListener createButtonListener(GuiSchematicSaveBase.ButtonType type) {
        return new GuiFileImport.ButtonListener(type, this);
    }

    @Override
    public void onTaskCompleted() {
        if (this.mc.isOnThread()) {
            this.refreshList();
        } else {
            this.mc.execute(GuiFileImport.this::refreshList);
        }
    }

    private void refreshList() {
        if (GuiUtils.getCurrentScreen() == this) {
            this.getListWidget().refreshEntries();
            this.getListWidget().clearSchematicMetadataCache();
        }
    }

    private static class ButtonListener implements IButtonActionListener {
        private final GuiFileImport gui;
        private final GuiSchematicSaveBase.ButtonType type;

        public ButtonListener(GuiSchematicSaveBase.ButtonType type, GuiFileImport gui) {
            this.type = type;
            this.gui = gui;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            if (this.type == GuiSchematicSaveBase.ButtonType.SAVE) {
                File dir = this.gui.getListWidget().getCurrentDirectory();
                String fileName = this.gui.getTextFieldText();

                if (!dir.isDirectory()) {
                    this.gui.addMessage(Message.MessageType.ERROR, "litematica.error.schematic_save.invalid_directory", dir.getAbsolutePath());
                    return;
                }

                if (fileName.isEmpty()) {
                    this.gui.addMessage(Message.MessageType.ERROR, "litematica.error.schematic_save.invalid_schematic_name", fileName);
                    return;
                }

                LitematicaSchematic schematic = this.gui.schematic;
                schematic.getMetadata().setTimeModified(System.currentTimeMillis());

                if (schematic.writeToFile(dir, fileName, GuiBase.isShiftDown())) {
                    this.gui.addMessage(Message.MessageType.SUCCESS, "litematica.message.schematic_saved_as", fileName);
                    this.gui.getListWidget().refreshEntries();
                }
            }
        }
    }
}
