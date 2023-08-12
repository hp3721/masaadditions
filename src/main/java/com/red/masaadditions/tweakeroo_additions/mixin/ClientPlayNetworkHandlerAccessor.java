package com.red.masaadditions.tweakeroo_additions.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.message.LastSeenMessagesCollector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayNetworkHandler.class)
public interface ClientPlayNetworkHandlerAccessor {
    @Accessor
    LastSeenMessagesCollector getLastSeenMessagesCollector();
}
