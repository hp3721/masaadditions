package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockDustParticle.class)
public abstract class MixinBlockDustParticle extends Particle {
    protected MixinBlockDustParticle(ClientWorld worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    // From UsefulMod by nessie
    @Inject(method = "<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V", at = @At("RETURN"))
    private void removeRandomParticleMotion(CallbackInfo ci) {
        if (FeatureToggleExtended.TWEAK_INSANE_BLOCK_BREAKING_PARTICLES.getBooleanValue()) {
            final double multiplier = this.random.nextFloat() * 5;
            this.velocityX *= multiplier;
            this.velocityY *= multiplier;
            this.velocityZ *= multiplier;
            this.maxAge *= multiplier;
            this.gravityStrength = 0F;
        }
    }
}
