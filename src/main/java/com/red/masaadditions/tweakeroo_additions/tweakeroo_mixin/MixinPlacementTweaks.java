package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.MasaAdditions;
import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import fi.dy.masa.tweakeroo.util.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = PlacementTweaks.class, remap = false)
public class MixinPlacementTweaks {
    @Inject(method = "onProcessRightClickBlock", at = @At("HEAD"), cancellable = true)
    private static void onProcessRightClickBlock(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
        Item heldItem = player.getStackInHand(hand).getItem();

        if (MiscUtils.handleUseDragonEgg(block, player) || MiscUtils.handleUseBed(block, world) || MiscUtils.handleUseTools(block, heldItem)) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "onTick", at = @At("RETURN"))
    private static void onTick(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.player != null && ConfigsExtended.Generic.HAND_RESTOCK_CONTINUOUS.getBooleanValue() && GuiUtils.getCurrentScreen() == null)
        {
            InventoryUtils.preRestockHand(mc.player, Hand.MAIN_HAND, true);
            InventoryUtils.preRestockHand(mc.player, Hand.OFF_HAND, true);
        }
    }
}
