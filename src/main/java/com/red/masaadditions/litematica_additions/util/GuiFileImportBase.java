package com.red.masaadditions.litematica_additions.util;

import fi.dy.masa.litematica.gui.GuiSchematicBrowserBase;
import fi.dy.masa.litematica.gui.GuiSchematicSave;
import fi.dy.masa.litematica.gui.GuiSchematicSaveBase;
import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

public abstract class GuiFileImportBase extends GuiSchematicBrowserBase implements ISelectionListener<WidgetFileBrowserBase.DirectoryEntry> {
    protected GuiTextFieldGeneric textField;
    protected String lastText = "";
    protected String defaultText = "";
    protected final LitematicaSchematic schematic;

    public GuiFileImportBase(LitematicaSchematic schematic) {
        super(10, 70);

        this.schematic = schematic;

        this.textField = new GuiTextFieldGeneric(10, 32, 160, 20, this.textRenderer);
        this.textField.setMaxLength(256);
        this.textField.setFocused(true);
    }

    @Override
    public int getBrowserHeight() {
        return this.height - 80;
    }

    @Override
    public void initGui() {
        super.initGui();

        boolean focused = this.textField.isFocused();
        String text = this.textField.getText();
        int pos = this.textField.getCursorPosition();
        this.textField = new GuiTextFieldGeneric(10, 32, this.width - 196, 20, this.textRenderer);
        this.textField.setText(text);
        this.textField.setCursorPosition(pos);
        this.textField.setFocused(focused);

        WidgetFileBrowserBase.DirectoryEntry entry = this.getListWidget().getLastSelectedEntry();

        // Only set the text field contents if it hasn't been set already.
        // This prevents overwriting any user input text when switching to a newly created directory.
        if (this.lastText.isEmpty()) {
            if (entry != null && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.DIRECTORY && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.INVALID) {
                this.setTextFieldText(FileUtils.getNameWithoutExtension(entry.getName()));
            } else if (this.schematic != null) {
                this.setTextFieldText(this.schematic.getMetadata().getName());
            } else {
                this.setTextFieldText(this.defaultText);
            }
        }

        this.createButton(this.textField.getX() + this.textField.getWidth() + 12, 32);
    }

    protected void setTextFieldText(String text) {
        this.lastText = text;
        this.textField.setText(text);
        this.textField.setCursorPositionEnd();
    }

    protected String getTextFieldText() {
        return this.textField.getText();
    }

    protected abstract IButtonActionListener createButtonListener(GuiSchematicSaveBase.ButtonType type);

    private int createButton(int x, int y) {
        String label = StringUtils.translate(GuiSchematicSave.ButtonType.SAVE.getLabelKey());
        int width = this.getStringWidth(label) + 10;
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, label, "litematica.gui.label.schematic_save.hoverinfo.hold_shift_to_overwrite");
        this.addButton(button, this.createButtonListener(GuiSchematicSave.ButtonType.SAVE));
        return x + width + 4;
    }

    @Override
    public void setString(String string) {
        this.setNextMessageType(Message.MessageType.ERROR);
        super.setString(string);
    }

    @Override
    public void drawContents(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.drawContents(matrixStack, mouseX, mouseY, partialTicks);

        this.textField.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onSelectionChange(@Nullable WidgetFileBrowserBase.DirectoryEntry entry) {
        if (entry != null && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.DIRECTORY && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.INVALID) {
            this.setTextFieldText(FileUtils.getNameWithoutExtension(entry.getName()));
        }
    }

    @Override
    protected ISelectionListener<WidgetFileBrowserBase.DirectoryEntry> getSelectionListener() {
        return this;
    }

    @Override
    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.textField.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }

        return super.onMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers) {
        if (this.textField.keyPressed(keyCode, scanCode, modifiers)) {
            this.getListWidget().clearSelection();
            return true;
        } else if (keyCode == KeyCodes.KEY_TAB) {
            this.textField.setFocused(!this.textField.isFocused());
            return true;
        }

        return super.onKeyTyped(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean onCharTyped(char charIn, int modifiers) {
        if (this.textField.charTyped(charIn, modifiers)) {
            this.getListWidget().clearSelection();
            return true;
        }

        return super.onCharTyped(charIn, modifiers);
    }
}
