package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import fi.dy.masa.tweakeroo.tweaks.MiscTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Pseudo
@Mixin(value = MiscTweaks.class, remap = false)
public class MixinMiscTweaks {
    @ModifyVariable(method = "doPotionWarnings", at = @At(value = "STORE", target = "Lfi/dy/masa/tweakeroo/tweaks/MiscTweaks;doPotionWarnings(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private static Collection<StatusEffectInstance> doPotionWarnings(Collection<StatusEffectInstance> effects) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        return player != null && player.getEquippedStack(EquipmentSlot.HEAD).getItem() != Items.TURTLE_HELMET ? effects : effects.stream().filter(e -> e.getEffectType() != StatusEffects.WATER_BREATHING).collect(Collectors.toCollection(TreeSet::new));
    }
}
