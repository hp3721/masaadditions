package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ItemFrameEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemFrameEntityRenderer.class)
public class MixinItemFrameEntityRenderer {
    private ItemFrameEntity itemFrameEntity;

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void disableItemFrameFrameRendering(ItemFrameEntity itemFrameEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        this.itemFrameEntity = itemFrameEntity;
    }

    @ModifyVariable(method = "render", at = @At("STORE"))
    private boolean disableItemFrameFrameRendering(boolean bl) {
        return ConfigsExtended.Disable.DISABLE_ITEM_FRAME_FRAME_RENDERING.getBooleanValue() && !itemFrameEntity.getHeldItemStack().isEmpty() || itemFrameEntity.isInvisible();
    }
}
