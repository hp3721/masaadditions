package com.red.masaadditions.minihud_additions.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.minihud.MiniHUD;
import fi.dy.masa.minihud.hotkeys.KeyCallbackToggleRenderer;

import javax.annotation.Nullable;

public enum RendererToggleExtended implements IHotkeyTogglable, IConfigNotifiable<IConfigBoolean> {
    OVERLAY_BEACON_RANGE("overlayBeaconRange", "", "Toggles the Beacon Range overlay renderer.\nPorted from 1.12 MiniHUD", "Beacon Range overlay");

    private final String name;
    private final String prettyName;
    private final String comment;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private boolean valueBoolean;
    @Nullable
    private IValueChangeCallback<IConfigBoolean> callback;

    RendererToggleExtended(String name, String defaultHotkey, String comment, String prettyName) {
        this(name, defaultHotkey, KeybindSettings.DEFAULT, comment, prettyName);
    }

    RendererToggleExtended(String name, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        this.name = name;
        this.prettyName = prettyName;
        this.comment = comment;
        this.defaultValueBoolean = true;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleRenderer(this));
    }

    @Override
    public ConfigType getType() {
        return ConfigType.HOTKEY;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPrettyName() {
        return this.prettyName;
    }

    @Override
    public String getStringValue() {
        return String.valueOf(this.valueBoolean);
    }

    @Override
    public String getDefaultStringValue() {
        return String.valueOf(this.defaultValueBoolean);
    }

    @Override
    public String getComment() {
        return comment != null ? this.comment : "";
    }

    @Override
    public boolean getBooleanValue() {
        return this.valueBoolean;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultValueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value) {
        boolean oldValue = this.valueBoolean;
        this.valueBoolean = value;

        if (oldValue != this.valueBoolean) {
            this.onValueChanged();
        }
    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<IConfigBoolean> callback) {
        this.callback = callback;
    }

    @Override
    public void onValueChanged() {
        if (this.callback != null) {
            this.callback.onValueChanged(this);
        }
    }

    @Override
    public IKeybind getKeybind() {
        return this.keybind;
    }

    @Override
    public boolean isModified() {
        return this.valueBoolean != this.defaultValueBoolean;
    }

    @Override
    public boolean isModified(String newValue) {
        return String.valueOf(this.defaultValueBoolean).equals(newValue) == false;
    }

    @Override
    public void resetToDefault() {
        this.valueBoolean = this.defaultValueBoolean;
    }

    @Override
    public void setValueFromString(String value) {
        try {
            this.valueBoolean = Boolean.parseBoolean(value);
        } catch (Exception e) {
            MiniHUD.logger.warn("Failed to read config value for {} from the JSON config", this.getName(), e);
        }
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            if (element.isJsonPrimitive()) {
                this.valueBoolean = element.getAsBoolean();
            } else {
                MiniHUD.logger.warn("Failed to read config value for {} from the JSON config", this.getName());
            }
        } catch (Exception e) {
            MiniHUD.logger.warn("Failed to read config value for {} from the JSON config", this.getName(), e);
        }
    }

    @Override
    public JsonElement getAsJsonElement() {
        return new JsonPrimitive(this.valueBoolean);
    }
}
