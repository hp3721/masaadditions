package com.red.masaadditions.tweakeroo_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.*;

public class ConfigsExtended {
    public static class Generic {
        public static final ConfigInteger BLOCK_BREAKING_PARTICLE_LIMIT = new ConfigInteger("blockBreakingParticleLimit", 8, 1, 1024, "This controls the maximum number of block breaking\nparticles produced per block broken if the\n'Block Breaking Particle Tweaks' tweak is enabled.\nThe default in vanilla is 64 particles per block.\nPorted from 1.12 Tweakeroo.");
        public static final ConfigDouble BLOCK_BREAKING_PARTICLE_SCALE = new ConfigDouble("blockBreakingParticleScale", 1.0, 0, 20D, "This is just an extra option for some fun looking\nblock breaking particles. Works if you have the\n'Block Breaking Particle Tweaks' tweak enabled.\nPorted from 1.12 Tweakeroo.");
        public static final ConfigDouble BLOCK_BREAKING_PARTICLE_SPEED = new ConfigDouble("blockBreakingParticleSpeedMultiplier", 1.0, 0, 20D, "This is just an extra option for some fun looking\nblock breaking particles. Works if you have the\n'Block Breaking Particle Tweaks' tweak enabled.\nPorted from 1.12 Tweakeroo.");
        public static final ConfigBoolean CLICK_RECIPE_CRAFT = new ConfigBoolean("clickRecipeCraft", false, "Craft a recipe by clicking on it in the recipe book\nwhile holding CTRL and SHIFT modifier keys.\nHold ALT modifier key to throw crafted items.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBoolean DERPY_CHICKEN = new ConfigBoolean("derpyChicken", false, "Makes chickens always face upwards.\nThis feature is originally from CutelessMod by nessie.");
        public static final ConfigInteger SCOREBOARD_SIDEBAR_MAX_LENGTH = new ConfigInteger("scoreboardSidebarMaxLength", 15, 1, 30, "This controls the maximum length of the scoreboard sidebar.");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                BLOCK_BREAKING_PARTICLE_LIMIT,
                BLOCK_BREAKING_PARTICLE_SCALE,
                BLOCK_BREAKING_PARTICLE_SPEED,
                CLICK_RECIPE_CRAFT,
                DERPY_CHICKEN,
                SCOREBOARD_SIDEBAR_MAX_LENGTH
        );
    }

    public static class Fixes {
        public static final ConfigBoolean MINING_GHOST_BLOCK_FIX = new ConfigBoolean("miningGhostBlockFix", false, "Reduces ghost blocks while mining by sending\nuse item packets on the mined block.\nThis feature is originally from UsefulMod by nessie.");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                MINING_GHOST_BLOCK_FIX
        );
    }

    public static class Lists {
        public static final ConfigStringList PERIMETER_OUTLINE_BLOCKS_LIST = new ConfigStringList("perimeterOutlineBlocksList", ImmutableList.of(), "The block types checked by the Perimeter Wall Dig Helper tweak.");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                PERIMETER_OUTLINE_BLOCKS_LIST
        );
    }

    public static class Disable {
        public static final ConfigBooleanHotkeyed DISABLE_BEACON_BEAM_RENDERING = new ConfigBooleanHotkeyed("disableBeaconBeamRendering", false, "", "Disables rendering of beacon beams.");
        public static final ConfigBooleanHotkeyed DISABLE_BOSS_BAR_RENDERING = new ConfigBooleanHotkeyed("disableBossBarRendering", false, "", "Disables rendering of boss bars.");
        public static final ConfigBooleanHotkeyed DISABLE_DRAGON_EGG_TELEPORTING = new ConfigBooleanHotkeyed("disableDragonEggTeleporting", false, "", "Prevents player from attacking (outside of creative mode)\nor using dragon eggs.");
        public static final ConfigBooleanHotkeyed DISABLE_FLOWER_BLOCK_MODEL_OFFSET = new ConfigBooleanHotkeyed("disableFlowerBlockModelOffset", false, "", "Disables the XZ offsetting of the flower block model.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBooleanHotkeyed DISABLE_ITEM_FRAME_FRAME_RENDERING = new ConfigBooleanHotkeyed("disableItemFrameFrameRendering", false, "", "Disables rendering of item frame frames.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBooleanHotkeyed DISABLE_REALMS_BUTTON = new ConfigBooleanHotkeyed("disableRealmsButton", false, "", "Disables the 'Minecraft Realms' button on the title screen.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBooleanHotkeyed DISABLE_SCOREBOARD_SIDEBAR_RENDERING = new ConfigBooleanHotkeyed("disableScoreboardSidebarRendering", false, "", "Disables rendering of scoreboard sidebar.");
        public static final ConfigBooleanHotkeyed DISABLE_SLIME_BLOCK_BOUNCING = new ConfigBooleanHotkeyed("disableSlimeBlockBouncing", false, "", "Prevents player from bouncing on slime blocks.");
        public static final ConfigBooleanHotkeyed DISABLE_SPRINTING_UNDERWATER = new ConfigBooleanHotkeyed("disableSprintingUnderwater", true, "", "Prevents player from sprinting underwater with the Disable Swimming tweak.");
        public static final ConfigBooleanHotkeyed DISABLE_SWIMMING = new ConfigBooleanHotkeyed("disableSwimming", false, "", "Disables swimming while sprinting underwater.");

        public static final ImmutableList<IHotkeyTogglable> ADDITIONAL_OPTIONS = ImmutableList.of(
                DISABLE_BEACON_BEAM_RENDERING,
                DISABLE_BOSS_BAR_RENDERING,
                DISABLE_DRAGON_EGG_TELEPORTING,
                DISABLE_FLOWER_BLOCK_MODEL_OFFSET,
                DISABLE_ITEM_FRAME_FRAME_RENDERING,
                DISABLE_REALMS_BUTTON,
                DISABLE_SCOREBOARD_SIDEBAR_RENDERING,
                DISABLE_SLIME_BLOCK_BOUNCING,
                DISABLE_SPRINTING_UNDERWATER,
                DISABLE_SWIMMING
        );
    }
}
