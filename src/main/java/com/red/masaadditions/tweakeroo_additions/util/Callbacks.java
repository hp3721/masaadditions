package com.red.masaadditions.tweakeroo_additions.util;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import com.red.masaadditions.tweakeroo_additions.mixin.MixinAbstractBlockAccessor;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.tweakeroo.util.RayTraceUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Callbacks {
    public static class KeyCallbackHotkeysGeneric implements IHotkeyCallback {
        private final MinecraftClient mc = MinecraftClient.getInstance();

        // From 1.12 Tweakeroo by Masa
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == HotkeysExtended.BLINK_DRIVE.getKeybind()) {
                this.blinkDriveTeleport(false);
                return true;
            } else if (key == HotkeysExtended.BLINK_DRIVE_Y_LEVEL.getKeybind()) {
                this.blinkDriveTeleport(true);
                return true;
            }

            return false;
        }

        private void blinkDriveTeleport(boolean maintainY) {
            if (this.mc.player.isCreative()) {
                Entity entity = fi.dy.masa.malilib.util.EntityUtils.getCameraEntity();
                HitResult trace = RayTraceUtils.getRayTraceFromEntity(this.mc.world, entity, true, this.mc.options.getClampedViewDistance() * 16 + 200);

                if (trace.getType() != HitResult.Type.MISS) {
                    Vec3d pos = trace.getPos();
                    if (trace.getType() == HitResult.Type.BLOCK) {
                        pos = adjustPositionToSideOfEntity(pos, this.mc.player, ((BlockHitResult) trace).getSide());
                    }

                    this.mc.player.networkHandler.sendCommand(String.format("tp @p %.6f %.6f %.6f", pos.x, maintainY ? this.mc.player.getY() : pos.y, pos.z));
                }
            }
        }

        public static Vec3d adjustPositionToSideOfEntity(Vec3d pos, Entity entity, Direction side) {
            double x = pos.x;
            double y = pos.y;
            double z = pos.z;

            if (side == Direction.DOWN) {
                y -= entity.getHeight();
            } else if (side.getAxis().isHorizontal()) {
                x += side.getOffsetX() * (entity.getWidth() / 2 + 1.0E-4D);
                z += side.getOffsetZ() * (entity.getWidth() / 2 + 1.0E-4D);
            }

            return new Vec3d(x, y, z);
        }
    }

    public static class KeyCallbackToggleFastRightClick extends KeyCallbackToggleBooleanConfigWithMessage {
        public KeyCallbackToggleFastRightClick(IConfigBoolean config) {
            super(config);
        }

        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (ConfigsExtended.Disable.DISABLE_FARMLAND_MAKING.getBooleanValue()) {
                this.config.setBooleanValue(false);
                String message = StringUtils.translate("masaadditions.message.fast_right_click_disabled");
                InfoUtils.printActionbarMessage(message);
            } else {
                super.onKeyAction(action, key);
            }
            return true;
        }
    }

    public static class FeatureCallbackHoney implements IValueChangeCallback<ConfigBoolean> {
        public FeatureCallbackHoney(ConfigBoolean feature) {
            if (feature.equals(ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_SLOWDOWN)) {
                ConfigsExtended.Internal.HONEY_BLOCK_VELOCITY_MULTIPLIER_ORIGINAL.setDoubleValue(Blocks.HONEY_BLOCK.getVelocityMultiplier());

                if (feature.getBooleanValue()) {
                    ((MixinAbstractBlockAccessor) Blocks.HONEY_BLOCK).setVelocityMultiplier(Blocks.STONE.getVelocityMultiplier());
                }
            } else {
                ConfigsExtended.Internal.HONEY_BLOCK_JUMP_VELOCITY_MULTIPLIER_ORIGINAL.setDoubleValue(Blocks.HONEY_BLOCK.getJumpVelocityMultiplier());

                if (!feature.getBooleanValue()) {
                    ((MixinAbstractBlockAccessor) Blocks.HONEY_BLOCK).setJumpVelocityMultiplier(Blocks.STONE.getJumpVelocityMultiplier());
                }
            }
        }

        @Override
        public void onValueChanged(ConfigBoolean config) {
            if (config.equals(ConfigsExtended.Disable.DISABLE_HONEY_BLOCK_SLOWDOWN)) {
                if (config.getBooleanValue()) {
                    ((MixinAbstractBlockAccessor) Blocks.HONEY_BLOCK).setVelocityMultiplier(Blocks.STONE.getVelocityMultiplier());
                } else {
                    ((MixinAbstractBlockAccessor) Blocks.HONEY_BLOCK).setVelocityMultiplier((float) ConfigsExtended.Internal.HONEY_BLOCK_VELOCITY_MULTIPLIER_ORIGINAL.getDoubleValue());
                }
            } else {
                if (config.getBooleanValue()) {
                    ((MixinAbstractBlockAccessor) Blocks.HONEY_BLOCK).setJumpVelocityMultiplier((float) ConfigsExtended.Internal.HONEY_BLOCK_JUMP_VELOCITY_MULTIPLIER_ORIGINAL.getDoubleValue());
                } else {
                    ((MixinAbstractBlockAccessor) Blocks.HONEY_BLOCK).setJumpVelocityMultiplier(Blocks.STONE.getJumpVelocityMultiplier());
                }
            }
        }
    }
}
