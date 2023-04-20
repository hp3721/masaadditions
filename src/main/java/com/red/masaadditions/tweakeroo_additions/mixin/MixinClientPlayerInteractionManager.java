package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.NameTagItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.StreamSupport;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void handleBreakingRestriction1(BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (PlacementTweaks.onProcessLeftClickBlock(pos) || PlacementTweaks.isPositionDisallowedByPerimeterOutlineList(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), cancellable = true)
    private void handleBreakingRestriction2(BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (PlacementTweaks.onProcessLeftClickBlock(pos) || PlacementTweaks.isPositionDisallowedByPerimeterOutlineList(pos)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"))
    private void onAttackEntity1(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (FeatureToggleExtended.TWEAK_ONE_HIT_KILL.getBooleanValue() && player.isCreative() && target instanceof LivingEntity && ((LivingEntity) target).getHealth() > 0f) {
            ((ClientPlayerEntity) player).sendCommand(String.format("/kill %s", target.getUuidAsString()));
        }
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity2(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (FeatureToggleExtended.TWEAK_PREVENT_ATTACK_ENTITIES.getBooleanValue() && ConfigsExtended.Lists.PREVENT_ATTACK_ENTITIES_LIST.getStrings().contains(EntityType.getId(target.getType()).toString())) {
            ci.cancel();
        }
    }

    @Inject(method = "interactEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V", shift = At.Shift.AFTER), cancellable = true)
    private void onInteractEntity(PlayerEntity player, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (FeatureToggleExtended.TWEAK_NAME_TAG_PIGLINS.getBooleanValue() && player.getStackInHand(hand).getItem() instanceof NameTagItem) {
            if (!(entity instanceof PiglinEntity piglinEntity)) {
                cir.setReturnValue(ActionResult.PASS);
                return;
            }

            if (piglinEntity.isBaby() || piglinEntity.getCustomName() != null || StreamSupport.stream(piglinEntity.getHandItems().spliterator(), false).noneMatch(itemStack -> itemStack.getItem() instanceof SwordItem)) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
