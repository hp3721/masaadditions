package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = {ArmorItem.class, ElytraItem.class})
public class MixinArmorItem {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/TypedActionResult;fail(Ljava/lang/Object;)Lnet/minecraft/util/TypedActionResult;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, ItemStack itemStack) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (!FeatureToggleExtended.TWEAK_FORCE_SWAP_GEAR.getBooleanValue() || !user.isSneaking() || hand != Hand.MAIN_HAND || mc.interactionManager == null || user.currentScreenHandler != user.playerScreenHandler) {
            return;
        }

        mc.interactionManager.clickSlot(user.playerScreenHandler.syncId, MiscUtils.getSlotNumberForEquipmentSlot(MobEntity.getPreferredEquipmentSlot(itemStack)), user.inventory.selectedSlot, SlotActionType.SWAP, user);
        cir.setReturnValue(TypedActionResult.success(itemStack, world.isClient()));
    }
}
