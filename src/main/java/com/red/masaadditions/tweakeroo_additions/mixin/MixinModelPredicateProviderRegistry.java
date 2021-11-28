package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.item.ModelPredicateProviderRegistry$1")
public class MixinModelPredicateProviderRegistry {
    @Redirect(method = "Lnet/minecraft/client/item/ModelPredicateProviderRegistry$1;unclampedCall(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;I)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getSkyAngle(F)F"))
    private float getSkyAngle(ClientWorld clientWorld, float tickDelta) {
        return clientWorld.getDimension().getSkyAngle(clientWorld.getLevelProperties().getTimeOfDay());
    }
}