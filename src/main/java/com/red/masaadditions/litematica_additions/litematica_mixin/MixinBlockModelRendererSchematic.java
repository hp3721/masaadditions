package com.red.masaadditions.litematica_additions.litematica_mixin;

import com.red.masaadditions.litematica_additions.config.ConfigsExtended;
import com.red.masaadditions.litematica_additions.util.MiscUtils;
import fi.dy.masa.litematica.render.schematic.BlockModelRendererSchematic;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockModelRendererSchematic.class, remap = false)
public class MixinBlockModelRendererSchematic {
    @Inject(method = "shouldRenderModelSide", at = @At("RETURN"), cancellable = true)
    private void shouldRenderModelSide(BlockRenderView worldIn, BlockState stateIn, BlockPos posIn, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            return;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ItemStack item = player != null ? player.getMainHandStack() : ItemStack.EMPTY;
        BlockState neighborBlockState = worldIn.getBlockState(posIn.offset(side));
        cir.setReturnValue(ConfigsExtended.Generic.RENDER_HELD_ITEM_ONLY.getBooleanValue() && MiscUtils.checkHeldItem(item, neighborBlockState));
    }
}
