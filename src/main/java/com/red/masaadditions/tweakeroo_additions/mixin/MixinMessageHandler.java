package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageHandler.class)
public class MixinMessageHandler {
    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    public void onGameMessage(Text message, boolean overlay, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_SLEEPING_NOTIFICATION.getBooleanValue() && message.getContent() instanceof TranslatableTextContent text && (text.getKey().equals("sleep.skipping_night") || text.getKey().equals("sleep.players_sleeping")))
            ci.cancel();
    }
}