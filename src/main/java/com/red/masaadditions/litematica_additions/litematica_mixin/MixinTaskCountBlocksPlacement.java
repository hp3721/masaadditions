package com.red.masaadditions.litematica_additions.litematica_mixin;

import com.red.masaadditions.litematica_additions.config.ConfigsExtended;
import fi.dy.masa.litematica.scheduler.tasks.TaskCountBlocksPlacement;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = TaskCountBlocksPlacement.class, remap = false)
public class MixinTaskCountBlocksPlacement {
    @Inject(method = "countAtPosition", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2IntOpenHashMap;addTo(Ljava/lang/Object;I)I", ordinal = 2), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void countAtPosition(BlockPos pos, CallbackInfo ci, BlockState stateSchematic, BlockState stateClient) {
        if (ConfigsExtended.Generic.MATERIAL_LIST_IGNORE_BLOCK_STATE.getBooleanValue() && stateSchematic.getBlock() == stateClient.getBlock()) {
            ci.cancel();
        }
    }
}
