package com.red.masaadditions.tweakeroo_additions.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.tweakeroo.Tweakeroo;

public enum FeatureToggleExtended implements IHotkeyTogglable, IConfigNotifiable<IConfigBoolean> {
    TWEAK_BLOCK_BREAKING_PARTICLES("tweakBlockBreakingParticleTweaks", false, "", "Allows tweaking the block breaking particles, such as reducing the number\nof particles produced per block broken.\nSet the limit in Generic -> 'Block Breaking Particle Limit'.\nPorted from 1.12 Tweakeroo."),
    TWEAK_PERIMETER_WALL_DIG_HELPER("tweakPerimeterWallDigHelper", false, "", "Prevents player from mining underneath the block types\nspecified in the Perimeter Outline Blocks list."),
    TWEAK_INSANE_BLOCK_BREAKING_PARTICLES("tweakInsaneBlockBreakingParticles", false, "", "Changes block breaking particles to have no gravity and increased velocity.\nThis feature is originally from UsefulMod by nessie."),
    TWEAK_ONE_HIT_KILL("tweakOneHitKill", false, "", "Enables one hit killing attacked living entities if player is in creative.\nRequires operator permission to use /kill."),
    TWEAK_ITEM_NAME_COPY("tweakItemNameCopy", false, "", "Sets item name in anvil to string stored in clipboard."),
    TWEAK_RESPAWN_ON_DEATH("tweakRespawnOnDeath", false, "", "Enables automatic respawning on death.\nThis feature is originally from UsefulMod by nessie.");

    private final String name;
    private final String comment;
    private final String prettyName;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final boolean singlePlayer;
    private boolean valueBoolean;
    private IValueChangeCallback<IConfigBoolean> callback;

    private FeatureToggleExtended(String name, boolean defaultValue, String defaultHotkey, String comment) {
        this(name, defaultValue, false, defaultHotkey, KeybindSettings.DEFAULT, comment);
    }

    private FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT, comment);
    }

    private FeatureToggleExtended(String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings, String comment) {
        this(name, defaultValue, false, defaultHotkey, settings, comment);
    }

    private FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment) {
        this(name, defaultValue, singlePlayer, defaultHotkey, settings, comment, StringUtils.splitCamelCase(name.substring(5)));
    }

    private FeatureToggleExtended(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName) {
        this(name, defaultValue, false, defaultHotkey, comment, prettyName);
    }

    private FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment, String prettyName) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT, comment, prettyName);
    }

    private FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.singlePlayer = singlePlayer;
        this.comment = comment;
        this.prettyName = prettyName;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
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
    public String getConfigGuiDisplayName() {
        if (this.singlePlayer) {
            return GuiBase.TXT_GOLD + this.getName() + GuiBase.TXT_RST;
        }

        return this.getName();
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
    public void setValueFromString(String value) {
    }

    @Override
    public void onValueChanged() {
        if (this.callback != null) {
            this.callback.onValueChanged(this);
        }
    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<IConfigBoolean> callback) {
        this.callback = callback;
    }

    @Override
    public String getComment() {
        if (this.comment == null) {
            return "";
        }

        if (this.singlePlayer) {
            return this.comment + "\n" + StringUtils.translate("tweakeroo.label.config_comment.single_player_only");
        } else {
            return this.comment;
        }
    }

    @Override
    public IKeybind getKeybind() {
        return this.keybind;
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
    public boolean isModified() {
        return this.valueBoolean != this.defaultValueBoolean;
    }

    @Override
    public boolean isModified(String newValue) {
        return Boolean.parseBoolean(newValue) != this.defaultValueBoolean;
    }

    @Override
    public void resetToDefault() {
        this.valueBoolean = this.defaultValueBoolean;
    }

    @Override
    public JsonElement getAsJsonElement() {
        return new JsonPrimitive(this.valueBoolean);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            if (element.isJsonPrimitive()) {
                this.valueBoolean = element.getAsBoolean();
            } else {
                Tweakeroo.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        } catch (Exception e) {
            Tweakeroo.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }
}
