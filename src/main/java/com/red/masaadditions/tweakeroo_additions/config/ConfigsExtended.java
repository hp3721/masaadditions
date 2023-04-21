package com.red.masaadditions.tweakeroo_additions.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.*;

public class ConfigsExtended {
    public static class Generic {
        public static final ConfigInteger BLOCK_BREAKING_PARTICLE_LIMIT = new ConfigInteger("blockBreakingParticleLimit", 8, 1, 1024, "This controls the maximum number of block breaking\nparticles produced per block broken if the\n'Block Breaking Particle Tweaks' tweak is enabled.\nThe default in vanilla is 64 particles per block.\nPorted from 1.12 Tweakeroo.");
        public static final ConfigDouble BLOCK_BREAKING_PARTICLE_SCALE = new ConfigDouble("blockBreakingParticleScale", 1, 0, 20, "This is just an extra option for some fun looking\nblock breaking particles. Works if you have the\n'Block Breaking Particle Tweaks' tweak enabled.\nPorted from 1.12 Tweakeroo.");
        public static final ConfigDouble BLOCK_BREAKING_PARTICLE_SPEED = new ConfigDouble("blockBreakingParticleSpeedMultiplier", 1, 0, 20, "This is just an extra option for some fun looking\nblock breaking particles. Works if you have the\n'Block Breaking Particle Tweaks' tweak enabled.\nPorted from 1.12 Tweakeroo.");
        public static final ConfigBoolean CLICK_RECIPE_CRAFT = new ConfigBoolean("clickRecipeCraft", false, "Craft a recipe by clicking on it in the recipe book\nwhile holding CTRL and SHIFT modifier keys.\nHold ALT modifier key to throw crafted items.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigDouble CLOUD_HEIGHT = new ConfigDouble("cloudHeight", 128, 0, 256, "This controls the height of clouds in the Overworld dimension.");
        public static final ConfigBoolean DERPY_CHICKEN = new ConfigBoolean("derpyChicken", false, "Makes chickens always face upwards.\nThis feature is originally from CutelessMod by nessie.");
        public static final ConfigBoolean HAND_RESTOCK_CONTINUOUS = new ConfigBoolean("handRestockContinuous", false, "If enabled, then hand restocking is attempted every tick,\nwhereas normally it only happens before and after\nleft/right clicks or in the fast block placement mode.\nPorted from 1.12 Tweakeroo.\n§6It is recommended to have this disabled, §6unless you\nknow you will need it for some specific use case.");
        public static final ConfigInteger SCOREBOARD_SIDEBAR_MAX_LENGTH = new ConfigInteger("scoreboardSidebarMaxLength", 15, 1, 30, "This controls the maximum length of the scoreboard sidebar.");
        public static final ConfigInteger SKY_TIME_OVERRIDE = new ConfigInteger("skyTimeOverride", 0, 0, 24000, "The day time to use when overriding the time used for rendering the sky if the Override Sky Time tweak is enabled.");
        public static final ConfigString WINDOW_TITLE_OVERRIDE = new ConfigString("windowTitleOverride", "", "The title used to override the default instance window title if the Override Window Title tweak is enabled.");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                BLOCK_BREAKING_PARTICLE_LIMIT,
                BLOCK_BREAKING_PARTICLE_SCALE,
                BLOCK_BREAKING_PARTICLE_SPEED,
                CLICK_RECIPE_CRAFT,
                CLOUD_HEIGHT,
                DERPY_CHICKEN,
                HAND_RESTOCK_CONTINUOUS,
                SCOREBOARD_SIDEBAR_MAX_LENGTH,
                SKY_TIME_OVERRIDE,
                WINDOW_TITLE_OVERRIDE
        );
    }

    public static class Lists {
        public static final ConfigStringList PERIMETER_OUTLINE_BLOCKS_LIST = new ConfigStringList("perimeterOutlineBlocksList", ImmutableList.of(), "The block types checked by the Perimeter Wall Dig Helper tweak.");
        public static final ConfigStringList PREVENT_ATTACK_ENTITIES_LIST = new ConfigStringList("preventAttackEntitiesList", ImmutableList.of(), "The entity types checked by the Prevent Attack Entities tweak.");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                PERIMETER_OUTLINE_BLOCKS_LIST,
                PREVENT_ATTACK_ENTITIES_LIST
        );
    }

    public static class Disable {
        public static final ConfigBooleanHotkeyed DISABLE_BEACON_BEAM_RENDERING = new ConfigBooleanHotkeyed("disableBeaconBeamRendering", false, "", "Disables rendering of beacon beams.");
        public static final ConfigBooleanHotkeyed DISABLE_BED_EXPLOSIONS = new ConfigBooleanHotkeyed("disableBedExplosions", false, "", "Prevents player from using beds while in the Nether or End\nwhich would cause the bed to explode.");
        public static final ConfigBooleanHotkeyed DISABLE_BLOCK_ATTACKED_PARTICLES = new ConfigBooleanHotkeyed("disableBlockAttackedParticles", false, "", "Removes block particles that are rendered on sides of blocks when attacked.");
        public static final ConfigBooleanHotkeyed DISABLE_BOSS_BAR_RENDERING = new ConfigBooleanHotkeyed("disableBossBarRendering", false, "", "Disables rendering of boss bars.");
        public static final ConfigBooleanHotkeyed DISABLE_CHRISTMAS_CHESTS = new ConfigBooleanHotkeyed("disableChristmasChests", false, "", "Disables the Christmas texture for chests.");
        public static final ConfigBooleanHotkeyed DISABLE_DRAGON_EGG_TELEPORTING = new ConfigBooleanHotkeyed("disableDragonEggTeleporting", false, "", "Prevents player from attacking (outside of creative mode)\nor using dragon eggs while not shifting.");
        public static final ConfigBooleanHotkeyed DISABLE_FARMLAND_MAKING = new ConfigBooleanHotkeyed("disableFarmlandMaking", false, "", "Disables making farmland with a hoe.\nFast right click is disabled while this is enabled.");
        public static final ConfigBooleanHotkeyed DISABLE_FOOTSTEP_PARTICLES = new ConfigBooleanHotkeyed("disableFootstepParticles", false, "", "Removes players' footstep particles.");
        public static final ConfigBooleanHotkeyed DISABLE_HONEY_BLOCK_SLOWDOWN = new ConfigBooleanHotkeyed("disableHoneyBlockSlowdown", false, "", "Removes the slowdown from walking on honey blocks.");
        public static final ConfigBooleanHotkeyed DISABLE_HONEY_BLOCK_JUMPING = new ConfigBooleanHotkeyed("disableHoneyBlockJumping", true, "", "Prevents player from jumping while on honey blocks.");
        public static final ConfigBooleanHotkeyed DISABLE_ITEM_FRAME_FRAME_RENDERING = new ConfigBooleanHotkeyed("disableItemFrameFrameRendering", false, "", "Disables rendering of item frame frames.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBooleanHotkeyed DISABLE_OTHER_PLAYER_RENDERING = new ConfigBooleanHotkeyed("disableOtherPlayerRendering", false, "", "Disables rendering of other players.");
        public static final ConfigBooleanHotkeyed DISABLE_PATH_MAKING = new ConfigBooleanHotkeyed("disablePathMaking", false, "", "Disables making path blocks with a shovel.");
        public static final ConfigBooleanHotkeyed DISABLE_PLANT_BLOCK_MODEL_OFFSET = new ConfigBooleanHotkeyed("disablePlantBlockModelOffset", false, "", "Disables the random XZ offsetting of plant block models.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBooleanHotkeyed DISABLE_REALMS_BUTTON = new ConfigBooleanHotkeyed("disableRealmsButton", false, "", "Disables the 'Minecraft Realms' button on the title screen.\nThis feature is originally from UsefulMod by nessie.");
        public static final ConfigBooleanHotkeyed DISABLE_SCOREBOARD_SIDEBAR_RENDERING = new ConfigBooleanHotkeyed("disableScoreboardSidebarRendering", false, "", "Disables rendering of scoreboard sidebar.");
        public static final ConfigBooleanHotkeyed DISABLE_SLIME_BLOCK_BOUNCING = new ConfigBooleanHotkeyed("disableSlimeBlockBouncing", false, "", "Prevents player from bouncing on slime blocks.");
        public static final ConfigBooleanHotkeyed DISABLE_SPRINTING_UNDERWATER = new ConfigBooleanHotkeyed("disableSprintingUnderwater", true, "", "Prevents player from sprinting underwater with the Disable Swimming tweak.\nAlso, prevents player from sprinting in one block tall water with the\nPermanent Sprint tweak, which is not possible in Vanilla, if enabled.");
        public static final ConfigBooleanHotkeyed DISABLE_SWIMMING = new ConfigBooleanHotkeyed("disableSwimming", false, "", "Disables swimming while sprinting underwater.");

        public static final ImmutableList<IHotkeyTogglable> ADDITIONAL_OPTIONS = ImmutableList.of(
                DISABLE_BEACON_BEAM_RENDERING,
                DISABLE_BED_EXPLOSIONS,
                DISABLE_BLOCK_ATTACKED_PARTICLES,
                DISABLE_BOSS_BAR_RENDERING,
                DISABLE_CHRISTMAS_CHESTS,
                DISABLE_DRAGON_EGG_TELEPORTING,
                DISABLE_FARMLAND_MAKING,
                DISABLE_FOOTSTEP_PARTICLES,
                DISABLE_HONEY_BLOCK_SLOWDOWN,
                DISABLE_HONEY_BLOCK_JUMPING,
                DISABLE_ITEM_FRAME_FRAME_RENDERING,
                DISABLE_OTHER_PLAYER_RENDERING,
                DISABLE_PATH_MAKING,
                DISABLE_PLANT_BLOCK_MODEL_OFFSET,
                DISABLE_REALMS_BUTTON,
                DISABLE_SCOREBOARD_SIDEBAR_RENDERING,
                DISABLE_SLIME_BLOCK_BOUNCING,
                DISABLE_SPRINTING_UNDERWATER,
                DISABLE_SWIMMING
        );
    }

    public static class Internal {
        public static final ConfigDouble HONEY_BLOCK_VELOCITY_MULTIPLIER_ORIGINAL = new ConfigDouble("honeyBlockVelocityMultiplierOriginal", 0.4, 0, 1, "The original velocity value of Honey Blocks");
        public static final ConfigDouble HONEY_BLOCK_JUMP_VELOCITY_MULTIPLIER_ORIGINAL = new ConfigDouble("honeyBlockJumpVelocityMultiplierOriginal", 0.5, 0, 1, "The original jump velocity value of Honey Blocks");

        public static final ImmutableList<IConfigBase> ADDITIONAL_OPTIONS = ImmutableList.of(
                HONEY_BLOCK_VELOCITY_MULTIPLIER_ORIGINAL,
                HONEY_BLOCK_JUMP_VELOCITY_MULTIPLIER_ORIGINAL
        );
    }
}
