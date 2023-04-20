package com.red.masaadditions.litematica_additions.litematica_mixin;

import com.red.masaadditions.litematica_additions.config.ConfigsExtended;
import com.red.masaadditions.litematica_additions.util.MiscUtils;
import fi.dy.masa.litematica.render.schematic.BufferBuilderCache;
import fi.dy.masa.litematica.render.schematic.ChunkCacheSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkRenderDataSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkRendererSchematicVbo;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(value = ChunkRendererSchematicVbo.class, remap = false)
public class MixinChunkRendererSchematicVbo {
    @Shadow
    protected ChunkCacheSchematic schematicWorldView;

    @Inject(method = "renderBlocksAndOverlay", at = @At("HEAD"), cancellable = true)
    private void renderBlocksAndOverlay(BlockPos pos, ChunkRenderDataSchematic data, Set<BlockEntity> tileEntities, Set<RenderLayer> usedLayers, MatrixStack matrices, BufferBuilderCache buffers, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ItemStack item = player != null ? player.getMainHandStack() : ItemStack.EMPTY;
        BlockState stateSchematic = this.schematicWorldView.getBlockState(pos);
        if (ConfigsExtended.Generic.RENDER_HELD_ITEM_ONLY.getBooleanValue() && MiscUtils.checkHeldItem(item, stateSchematic)) {
            ci.cancel();
        }
    }
}
