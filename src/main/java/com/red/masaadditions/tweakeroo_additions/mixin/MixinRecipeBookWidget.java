package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookWidget.class)
public abstract class MixinRecipeBookWidget {
    @Shadow
    protected MinecraftClient client;

    // From UsefulMod by nessie
    @Inject(method = "refreshInputs", at = @At("RETURN"))
    private void refreshInputs(CallbackInfo ci) {
        if (ConfigsExtended.Generic.CLICK_RECIPE_CRAFT.getBooleanValue() && client.currentScreen.hasControlDown() && client.currentScreen.hasShiftDown()) {
            client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 0, 1, client.currentScreen.hasAltDown() ? SlotActionType.THROW : SlotActionType.QUICK_MOVE, client.player);
        }
    }
}
