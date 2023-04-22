package com.red.masaadditions.tweakeroo_additions.util;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.mixin.MixinKeyBindingAccessor;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Random;

public class MiscUtils {
    public static final ArrayList<KeyBinding> MOVEMENT_HOLD_KEYS = new ArrayList<>();

    public static void setMovementHoldKeys(boolean enabled) {
        if (!enabled) {
            KeyBinding.updatePressedStates();
            return;
        }

        MOVEMENT_HOLD_KEYS.clear();
        GameOptions options = MinecraftClient.getInstance().options;
        KeyBinding[] movementKeys = { options.keyJump, options.keyLeft, options.keyRight, options.keyBack, options.keyForward };
        for (KeyBinding movementKey : movementKeys) {
            if (movementKey.isPressed()) {
                MOVEMENT_HOLD_KEYS.add(movementKey);
            }
        }
    }

    // From 1.12 Tweakeroo by Masa
    public static void addCustomBlockBreakingParticles(ParticleManager manager, ClientWorld world, Random rand, BlockPos pos, BlockState state) {
        if (state.getMaterial() != Material.AIR) {
            int limit = ConfigsExtended.Generic.BLOCK_BREAKING_PARTICLE_LIMIT.getIntegerValue();

            for (int i = 0; i < limit; ++i) {
                double x = ((double) pos.getX() + rand.nextDouble());
                double y = ((double) pos.getY() + rand.nextDouble());
                double z = ((double) pos.getZ() + rand.nextDouble());
                double speedX = (0.5 - rand.nextDouble());
                double speedY = (0.5 - rand.nextDouble());
                double speedZ = (0.5 - rand.nextDouble());

                manager.addParticle((new BlockDustParticleExt(world, x, y, z, speedX, speedY, speedZ, state, pos))
                        .move((float) ConfigsExtended.Generic.BLOCK_BREAKING_PARTICLE_SPEED.getDoubleValue())
                        .scale((float) ConfigsExtended.Generic.BLOCK_BREAKING_PARTICLE_SCALE.getDoubleValue()));
            }
        }
    }

    public static boolean handleUseSnowLayer(Block block, ClientPlayerEntity player) {
        return ConfigsExtended.Disable.DISABLE_SNOW_LAYER_STACKING.getBooleanValue() && block instanceof SnowBlock;
    }

    public static boolean handleUseDragonEgg(Block block, ClientPlayerEntity player) {
        return ConfigsExtended.Disable.DISABLE_DRAGON_EGG_TELEPORTING.getBooleanValue() && block instanceof DragonEggBlock && !player.isSneaking();
    }

    public static boolean handleUseBed(Block block, ClientWorld world) {
        return ConfigsExtended.Disable.DISABLE_BED_EXPLOSIONS.getBooleanValue() && block instanceof BedBlock && !world.getDimension().isBedWorking();
    }

    public static boolean handleUseTools(Block block, Item heldItem) {
        return (ConfigsExtended.Disable.DISABLE_FARMLAND_MAKING.getBooleanValue() && heldItem instanceof HoeItem && MiscUtils.isTillableBlock(block) || (ConfigsExtended.Disable.DISABLE_PATH_MAKING.getBooleanValue() && heldItem instanceof ShovelItem && block instanceof GrassBlock));
    }

    public static boolean isTillableBlock(Block block) {
        return block instanceof GrassBlock || block instanceof DirtPathBlock || block == Blocks.DIRT;
    }

    public static int getSlotNumberForEquipmentSlot(EquipmentSlot type) {
        return switch (type) {
            case HEAD -> 5;
            case CHEST -> 6;
            case LEGS -> 7;
            case FEET -> 8;
            default -> -1;
        };
    }
}
