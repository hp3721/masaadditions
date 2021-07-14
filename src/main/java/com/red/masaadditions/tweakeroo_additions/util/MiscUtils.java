package com.red.masaadditions.tweakeroo_additions.util;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.block.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class MiscUtils {
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

                manager.addParticle((new BlockDustParticleExt(world, x, y, z, speedX, speedY, speedZ, state))
                        .setBlockPos(pos)
                        .move((float) ConfigsExtended.Generic.BLOCK_BREAKING_PARTICLE_SPEED.getDoubleValue())
                        .scale((float) ConfigsExtended.Generic.BLOCK_BREAKING_PARTICLE_SCALE.getDoubleValue()));
            }
        }
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
        return block instanceof GrassBlock || block instanceof GrassPathBlock || block.is(Blocks.DIRT);
    }

    public static int getSlotNumberForEquipmentSlot(EquipmentSlot type)
    {
        switch (type)
        {
            case HEAD:
                return 5;
            case CHEST:
                return 6;
            case LEGS:
                return 7;
            case FEET:
                return 8;
            default:
                return -1;
        }
    }
}
