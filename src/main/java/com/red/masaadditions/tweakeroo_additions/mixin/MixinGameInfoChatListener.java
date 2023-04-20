package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.gui.GameInfoChatListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(GameInfoChatListener.class)
public class MixinGameInfoChatListener {
    @Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
    public void onChatMessage(MessageType type, Text message, UUID sender, CallbackInfo ci) {
        if (!(ConfigsExtended.Disable.DISABLE_SLEEPING_NOTIFICATION.getBooleanValue() && type == MessageType.GAME_INFO && sender == Util.NIL_UUID && message instanceof TranslatableText))
            return;

        TranslatableText text = (TranslatableText) message;
        if (text.getKey().equals("sleep.skipping_night") || text.getKey().equals("sleep.players_sleeping"))
            ci.cancel();
    }
}