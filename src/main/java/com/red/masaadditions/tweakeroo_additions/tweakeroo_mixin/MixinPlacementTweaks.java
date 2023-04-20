package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import fi.dy.masa.tweakeroo.util.InventoryUtils;
import fi.dy.masa.tweakeroo.util.PlacementRestrictionMode;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = PlacementTweaks.class, remap = false)
public class MixinPlacementTweaks {
    @Shadow
    private static boolean isPositionAllowedByRestrictions(BlockPos pos, Direction side, BlockPos posFirst, Direction sideFirst, boolean restrictionEnabled, PlacementRestrictionMode mode, boolean gridEnabled, int gridSize) {
        return false;
    }

    @Inject(method = "canPlaceBlockIntoPosition", at = @At("HEAD"), cancellable = true)
    private static void canPlaceBlockIntoPosition(World world, BlockPos pos, ItemPlacementContext useContext, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExtended.Disable.DISABLE_SNOW_LAYER_STACKING.getBooleanValue() && world.getBlockState(pos).getBlock().equals(Blocks.SNOW))
        {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "onProcessRightClickBlock", at = @At("HEAD"), cancellable = true)
    private static void onProcessRightClickBlock(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
        Item heldItem = player.getStackInHand(hand).getItem();

        if (MiscUtils.handleUseSnowLayer(block, player) || MiscUtils.handleUseDragonEgg(block, player) || MiscUtils.handleUseBed(block, world) || MiscUtils.handleUseTools(block, heldItem)) {
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

    @Redirect(method = "tryPlaceBlock", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/hotkeys/IKeybind;isKeybindHeld()Z", ordinal = 1))
    private static boolean isKeybindHeld1(IKeybind iKeybind) {
        return iKeybind.isKeybindHeld() || FeatureToggleExtended.TWEAK_FLEXIBLE_BLOCK_PLACEMENT_OFFSET_TOGGLE.getBooleanValue();
    }

    @Redirect(method = "tryPlaceBlock", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/hotkeys/IKeybind;isKeybindHeld()Z", ordinal = 2))
    private static boolean isKeybindHeld2(IKeybind iKeybind) {
        return iKeybind.isKeybindHeld() || FeatureToggleExtended.TWEAK_FLEXIBLE_BLOCK_PLACEMENT_ADJACENT_TOGGLE.getBooleanValue();
    }

    @Redirect(method = "isPositionAllowedByPlacementRestriction", at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;isPositionAllowedByRestrictions(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;ZLfi/dy/masa/tweakeroo/util/PlacementRestrictionMode;ZI)Z"), require = 0)
    private static boolean isPositionAllowedByPlacementRestriction1(BlockPos pos, Direction side, BlockPos posFirst, Direction sideFirst, boolean restrictionEnabled, PlacementRestrictionMode mode, boolean gridEnabled, int gridSize)
    {
        return handleGridRestriction(pos, side, posFirst, sideFirst, restrictionEnabled, mode, gridEnabled, gridSize);
    }

    @Redirect(method = "isPositionAllowedByPlacementRestriction", at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;isPositionAllowedByRestrictions(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;ZLfi/dy/masa/tweakeroo/util/PlacementRestrictionMode;ZI)Z"), require = 0)
    private static boolean isPositionAllowedByPlacementRestriction2(BlockPos pos, Direction side, BlockPos posFirst, Direction sideFirst, boolean restrictionEnabled, PlacementRestrictionMode mode, boolean gridEnabled, int gridSize)
    {
        return handleGridRestriction(pos, side, posFirst, sideFirst, restrictionEnabled, mode, gridEnabled, gridSize);
    }

    @Redirect(method = "isPositionAllowedByBreakingRestriction", at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;isPositionAllowedByRestrictions(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;ZLfi/dy/masa/tweakeroo/util/PlacementRestrictionMode;ZI)Z"), require = 0)
    private static boolean isPositionAllowedByBreakingRestriction1(BlockPos pos, Direction side, BlockPos posFirst, Direction sideFirst, boolean restrictionEnabled, PlacementRestrictionMode mode, boolean gridEnabled, int gridSize)
    {
        return handleGridRestriction(pos, side, posFirst, sideFirst, restrictionEnabled, mode, gridEnabled, gridSize);
    }

    @Redirect(method = "isPositionAllowedByBreakingRestriction", at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;isPositionAllowedByRestrictions(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;ZLfi/dy/masa/tweakeroo/util/PlacementRestrictionMode;ZI)Z"), require = 0)
    private static boolean isPositionAllowedByBreakingRestriction2(BlockPos pos, Direction side, BlockPos posFirst, Direction sideFirst, boolean restrictionEnabled, PlacementRestrictionMode mode, boolean gridEnabled, int gridSize)
    {
        return handleGridRestriction(pos, side, posFirst, sideFirst, restrictionEnabled, mode, gridEnabled, gridSize);
    }

    private static boolean handleGridRestriction(BlockPos pos, Direction side, BlockPos posFirst, Direction sideFirst, boolean restrictionEnabled, PlacementRestrictionMode mode, boolean gridEnabled, int gridSize)
    {
        if (gridEnabled)
        {
            if ((ConfigsExtended.Generic.GRID_RESTRICT_X.getBooleanValue() && (Math.abs(pos.getX() - posFirst.getX()) % gridSize) != 0) ||
                (ConfigsExtended.Generic.GRID_RESTRICT_Y.getBooleanValue() && (Math.abs(pos.getY() - posFirst.getY()) % gridSize) != 0) ||
                (ConfigsExtended.Generic.GRID_RESTRICT_Z.getBooleanValue() && (Math.abs(pos.getZ() - posFirst.getZ()) % gridSize) != 0))
            {
                return false;
            }
        }

        return isPositionAllowedByRestrictions(pos, side, posFirst, sideFirst, restrictionEnabled, mode, false, gridSize);
    }
}
