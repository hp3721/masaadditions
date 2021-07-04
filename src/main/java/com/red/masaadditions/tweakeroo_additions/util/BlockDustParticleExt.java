package com.red.masaadditions.tweakeroo_additions.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.world.ClientWorld;

public class BlockDustParticleExt extends BlockDustParticle {
    // From 1.12 Tweakeroo by Masa
    public BlockDustParticleExt(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
                                double xSpeedIn, double ySpeedIn, double zSpeedIn, BlockState state) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);
    }
}