package com.red.masaadditions.minihud_additions.mixin;

import com.red.masaadditions.minihud_additions.renderer.OverlayRendererBeaconRange;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// From 1.12 MiniHUD by Masa
@Mixin(BeaconBlockEntity.class)
public abstract class MixinBeaconBlockEntity {
    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/block/entity/BeaconBlockEntity;level:I", ordinal = 2), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void onUpdateSegmentsPost(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci, int i, int j, int k, BlockPos blockPos2, BeaconBlockEntity.BeamSegment beamSegment, int l, int n) {
        if (((MixinBeaconBlockEntityAccessor) blockEntity).getLevel() != n) {
            OverlayRendererBeaconRange.setNeedsUpdate();
        }
    }
}
