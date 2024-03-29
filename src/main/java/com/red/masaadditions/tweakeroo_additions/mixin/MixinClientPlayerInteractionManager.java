package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.NameTagItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.StreamSupport;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Unique
    float attackedBlockHardness;

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

    // From UselessMod by nessie
    @Inject(method = "attackBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;breakBlock(Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.BEFORE, ordinal = 1))
    private void preInstantMine(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!ConfigsExtended.Fixes.MINING_GHOST_BLOCK_FIX.getBooleanValue())
            return;
        final ClientWorld world = MinecraftClient.getInstance().world;
        attackedBlockHardness = world.getBlockState(pos).getHardness(world, pos);
    }

    // From UselessMod by nessie
    @Inject(method = "attackBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;breakBlock(Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER, ordinal = 1))
    private void postInstantMine(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExtended.Fixes.MINING_GHOST_BLOCK_FIX.getBooleanValue() && attackedBlockHardness > 0F) {
            final ClientPlayNetworkHandler connection = MinecraftClient.getInstance().getNetworkHandler();
            if (connection != null) {
                final BlockHitResult blockHitResult = new BlockHitResult(new Vec3d(0, 0, 0), direction, pos, false);
                connection.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult));
            }
        }
    }

    @Inject(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"), cancellable = true)
    private void onAttackEntity1(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (FeatureToggleExtended.TWEAK_ONE_HIT_KILL.getBooleanValue() && player.abilities.creativeMode && target instanceof LivingEntity && ((LivingEntity) target).getHealth() > 0f) {
            ((ClientPlayerEntity) player).sendChatMessage(String.format("/kill %s", target.getUuidAsString()));
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
            if (!(entity instanceof PiglinEntity)) {
                cir.setReturnValue(ActionResult.PASS);
                return;
            }

            PiglinEntity piglinEntity = (PiglinEntity)entity;
            if (piglinEntity.isBaby() || piglinEntity.getCustomName() != null || StreamSupport.stream(piglinEntity.getItemsHand().spliterator(), false).noneMatch(itemStack -> itemStack.getItem() instanceof SwordItem)) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
