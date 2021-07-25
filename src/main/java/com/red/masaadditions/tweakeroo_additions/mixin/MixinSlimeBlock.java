package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public class MixinSlimeBlock extends TransparentBlock {
    protected MixinSlimeBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    private void onEntityLand(BlockView world, Entity entity, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_SLIME_BLOCK_BOUNCING.getBooleanValue() && entity instanceof PlayerEntity) {
            super.onEntityLand(world, entity);
            ci.cancel();
        }
    }

    @Inject(method = "onLandedUpon", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"), cancellable = true)
    private void handleFallDamage(World world, BlockState state, BlockPos pos, Entity entity, float distance, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_SLIME_BLOCK_BOUNCING.getBooleanValue() && entity instanceof PlayerEntity) {
            super.onLandedUpon(world, state, pos, entity, distance);
            ci.cancel();
        }
    }
}
