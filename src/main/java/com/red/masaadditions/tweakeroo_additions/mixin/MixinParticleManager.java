package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import fi.dy.masa.tweakeroo.config.Configs;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;


@Mixin(ParticleManager.class)
public class MixinParticleManager {
    @Shadow
    protected ClientWorld world;
    @Shadow
    @Final
    private Random random;

    // From 1.12 Tweakeroo by Masa
    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void onAddBlockDestroyEffects1(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!Configs.Disable.DISABLE_BLOCK_BREAK_PARTICLES.getBooleanValue() && FeatureToggleExtended.TWEAK_BLOCK_BREAKING_PARTICLES.getBooleanValue()) {
            MiscUtils.addCustomBlockBreakingParticles((net.minecraft.client.particle.ParticleManager) (Object) this, this.world, this.random, pos, state);
            ci.cancel();
        }
    }

    @Inject(method = "addBlockBreakingParticles", at = @At("HEAD"), cancellable = true)
    private void onAddBlockDestroyEffects2(BlockPos pos, Direction direction, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_BLOCK_ATTACKED_PARTICLES.getBooleanValue()) {
            ci.cancel();
        }
    }
}
