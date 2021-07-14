package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Pseudo
@Mixin(targets = {"carpet.helpers.OptimizedExplosion"}, remap = false)
public class MixinExplosionCarpet {
    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "doExplosionB",
            slice = @Slice(to = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/explosion/Explosion;getAffectedBlocks()Ljava/util/List;", ordinal = 1, remap = true)),
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/particle/ParticleTypes;EXPLOSION_EMITTER:Lnet/minecraft/particle/DefaultParticleType;", remap = true))
    private static DefaultParticleType redirectSpawnParticles() {
        if (FeatureToggle.TWEAK_EXPLOSION_REDUCED_PARTICLES.getBooleanValue()) {
            return ParticleTypes.EXPLOSION;
        }

        return ParticleTypes.EXPLOSION_EMITTER;
    }
}
