package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.LunarWorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @ModifyVariable(method = "doRandomBlockDisplayTicks", at = @At(value = "STORE"))
    private boolean doRandomBlockDisplayTicks(boolean bl) {
        return FeatureToggleExtended.TWEAK_ALWAYS_RENDER_BARRIER_PARTICLES.getBooleanValue() || bl;
    }
}