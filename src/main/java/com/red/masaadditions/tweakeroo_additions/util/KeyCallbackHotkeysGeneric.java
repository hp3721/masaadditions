package com.red.masaadditions.tweakeroo_additions.util;

import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.tweakeroo.util.RayTraceUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class KeyCallbackHotkeysGeneric implements IHotkeyCallback {
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
        if (this.mc.player.abilities.creativeMode) {
            Entity entity = fi.dy.masa.malilib.util.EntityUtils.getCameraEntity();
            HitResult trace = RayTraceUtils.getRayTraceFromEntity(this.mc.world, entity, true, this.mc.options.viewDistance * 16 + 200);

            if (trace != null && trace.getType() != HitResult.Type.MISS) {
                Vec3d pos = trace.getPos();
                if (trace.getType() == HitResult.Type.BLOCK) {
                    pos = this.adjustPositionToSideOfEntity(pos, this.mc.player, ((BlockHitResult) trace).getSide());
                }

                this.mc.player.sendChatMessage(String.format("/tp @p %.6f %.6f %.6f", pos.x, maintainY ? this.mc.player.getY() : pos.y, pos.z));
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