package com.red.masaadditions.minihud_additions.mixin;

import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void blockDestroyed(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        handleBeaconBlockDestroyed(pos);
    }

    private void handleBeaconBlockDestroyed(BlockPos pos) {
        ClientWorld world = client.world;
        if (!RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue() || world == null)
            return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BeaconBlockEntity) {
            OverlayRendererBeaconRange.setNeedsUpdate();
        }
    }
}
