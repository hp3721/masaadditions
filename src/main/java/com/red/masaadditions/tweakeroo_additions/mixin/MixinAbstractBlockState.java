package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinAbstractBlockState {
    @Shadow public abstract Block getBlock();

    @Inject(method = "getModelOffset", at = @At("HEAD"), cancellable = true)
    private void getModelOffset(CallbackInfoReturnable<Vec3d> cir) {
        Block block = getBlock();
        boolean isPlant = block instanceof TallPlantBlock || block instanceof FlowerBlock;
        if (isPlant && ConfigsExtended.Disable.DISABLE_PLANT_BLOCK_MODEL_OFFSET.getBooleanValue()) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }
}
