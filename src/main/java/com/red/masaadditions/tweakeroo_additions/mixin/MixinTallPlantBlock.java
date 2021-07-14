package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.TallPlantBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TallPlantBlock.class)
public class MixinTallPlantBlock {
    // From UsefulMod by nessie
    @Inject(method = "getOffsetType", at = @At("HEAD"), cancellable = true)
    private void getOffsetType(CallbackInfoReturnable<AbstractBlock.OffsetType> cir) {
        if (ConfigsExtended.Disable.DISABLE_PLANT_BLOCK_MODEL_OFFSET.getBooleanValue()) {
            cir.setReturnValue(AbstractBlock.OffsetType.NONE);
        }
    }
}