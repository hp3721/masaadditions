package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = PlacementTweaks.class, remap = false)
public class MixinPlacementTweaks {
    @Inject(method = "onProcessRightClickBlock", at = @At("HEAD"), cancellable = true)
    private static void onProcessRightClickBlock(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (ConfigsExtended.Disable.DISABLE_DRAGON_EGG_TELEPORTING.getBooleanValue() && !player.isSneaking() && world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DragonEggBlock) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
