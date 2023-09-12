package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.tweakeroo_additions.tweaks.InventoryTweaks;
import fi.dy.masa.tweakeroo.util.InventoryUtils;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = InventoryUtils.class, remap = false)
public class MixinInventoryUtils {
    @Inject(method = "getMinDurability", at = @At(value = "FIELD", target = "Lfi/dy/masa/tweakeroo/config/Configs$Generic;ITEM_SWAP_DURABILITY_THRESHOLD:Lfi/dy/masa/malilib/config/options/ConfigInteger;"), cancellable = true)
    private static void applyCanSwapAlmostBrokenTool(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (!InventoryTweaks.canSwapAlmostBrokenTool(stack.getItem())) {
            cir.setReturnValue(0);
        }
    }
}
