package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaEntity.class)
public abstract class MixinLlamaEntity extends AbstractHorseEntity {
    protected MixinLlamaEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getPrimaryPassenger()Lnet/minecraft/entity/LivingEntity;", at = @At("HEAD"), cancellable = true)
    private void allowLamaSteering(CallbackInfoReturnable<LivingEntity> cir) {
        if (FeatureToggleExtended.TWEAK_LLAMA_STEERING.getBooleanValue()) {
            cir.setReturnValue(super.getPrimaryPassenger());
        }
    }
}
