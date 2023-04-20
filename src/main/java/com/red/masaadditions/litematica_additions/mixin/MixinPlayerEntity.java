package com.red.masaadditions.litematica_additions.mixin;

import com.red.masaadditions.litematica_additions.config.ConfigsExtended;
import fi.dy.masa.litematica.config.Configs;
import fi.dy.masa.litematica.util.SchematicWorldRefresher;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntity.class)
public class MixinPlayerEntity {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"))
    private void renderBlocksAndOverlay(CallbackInfo ci) {
        if (ConfigsExtended.Generic.RENDER_HELD_ITEM_ONLY.getBooleanValue() && Configs.Visuals.ENABLE_RENDERING.getBooleanValue() && Configs.Visuals.ENABLE_SCHEMATIC_RENDERING.getBooleanValue()) {
            SchematicWorldRefresher.INSTANCE.updateAll();
        }
    }
}
