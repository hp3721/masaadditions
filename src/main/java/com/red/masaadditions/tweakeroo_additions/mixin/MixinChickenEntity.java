package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class MixinChickenEntity extends AnimalEntity {
    public MixinChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) {
        super(entityType, world);
    }

    // From CutelessMod by nessie
    @Inject(method = "tickMovement", at = @At("RETURN"))
    private void derpyChicken(CallbackInfo ci) {
        if (ConfigsExtended.Generic.DERPY_CHICKEN.getBooleanValue())
            this.pitch = -90F;
    }
}
