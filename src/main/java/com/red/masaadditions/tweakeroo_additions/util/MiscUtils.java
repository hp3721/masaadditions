package com.red.masaadditions.tweakeroo_additions.util;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
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
}
