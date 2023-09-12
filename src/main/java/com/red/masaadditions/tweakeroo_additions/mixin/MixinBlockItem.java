package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BlockItem.class)
public class MixinBlockItem {
    @ModifyVariable(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", ordinal = 1, at = @At(value = "STORE", ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/item/BlockItem;getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;")))
    private ItemPlacementContext modifyPlacementContext(ItemPlacementContext context) {
        boolean useReplacementMode = HotkeysExtended.REPLACEMENT_MODE.getKeybind().isKeybindHeld()
            && tweakermore_areWeThePlayer(context)
            && (context.getPlayer().hasPermissionLevel(2) || MinecraftClient.getInstance().isInSingleplayer())
            && !context.canReplaceExisting();
        if (useReplacementMode) {
            ((MixinItemPlacementContextAccessor) context).setCanReplaceExisting(true);
            if (context.getPlayer() == MinecraftClient.getInstance().player) {
                PlacementTweaks.replacementModeUseStack = context.getStack();
            }
        }
        return context;
    }

    @Unique
    private boolean tweakermore_areWeThePlayer(ItemPlacementContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return false;
        }
        // something that works for both the client player and the server version of the client player
        return player.getUuid().equals(MinecraftClient.getInstance().player.getUuid());
    }
}
