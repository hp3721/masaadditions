package com.red.masaadditions.tweakeroo_additions.tweaks;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.mixin.MixinClientWorldAccessor;
import com.red.masaadditions.tweakeroo_additions.mixin.MixinPendingUpdateAccessor;
import com.red.masaadditions.tweakeroo_additions.mixin.MixinPendingUpdateManagerAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlacementTweaks {
    @Nullable
    public static ItemStack replacementModeUseStack = null;

    public static final ArrayList<Block> PERIMETER_OUTLINE_BLOCKS = new ArrayList<>();

    public static boolean onProcessLeftClickBlock(BlockPos pos) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        return ConfigsExtended.Disable.DISABLE_DRAGON_EGG_TELEPORTING.getBooleanValue() && player != null && !player.isCreative() && player.getEntityWorld().getBlockState(pos).getBlock() instanceof DragonEggBlock;
    }

    public static boolean isPositionDisallowedByPerimeterOutlineList(BlockPos pos) {
        boolean restrictionEnabled = FeatureToggleExtended.TWEAK_PERIMETER_WALL_DIG_HELPER.getBooleanValue();

        if (!restrictionEnabled)
            return false;

        ClientWorld world = MinecraftClient.getInstance().world;
        return world != null && PERIMETER_OUTLINE_BLOCKS.contains(world.getBlockState(world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).down()).getBlock());
    }

    public static void setPerimeterOutlineBlocks(List<String> blocks) {
        PERIMETER_OUTLINE_BLOCKS.clear();

        for (String str : blocks) {
            Block block = getBlockFromName(str);

            if (block != null)
                PERIMETER_OUTLINE_BLOCKS.add(block);
        }
    }

    @Nullable
    private static Block getBlockFromName(String name) {
        try {
            Identifier identifier = new Identifier(name);
            return Registry.BLOCK.getOrEmpty(identifier).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> getReplacementModeCommands(ItemStack stack, int sequenceId) {
        ClientWorld world = Objects.requireNonNull(MinecraftClient.getInstance().world);
        @SuppressWarnings("resource")
        PendingUpdateManager pendingUpdateManager = ((MixinClientWorldAccessor) world).tweakermore_getPendingUpdateManager();
        var blockPosToPendingUpdate = ((MixinPendingUpdateManagerAccessor) pendingUpdateManager).getBlockPosToPendingUpdate();

        List<String> commands = new ArrayList<>();

        NbtCompound blockEntityNbt = BlockItem.getBlockEntityNbt(stack);
        for (var entry : blockPosToPendingUpdate.long2ObjectEntrySet()) {
            int sequence = ((MixinPendingUpdateAccessor) entry.getValue()).getSequence();
            if (sequence != sequenceId) {
                continue;
            }
            BlockPos pos = BlockPos.fromLong(entry.getLongKey());
            BlockState state = world.getBlockState(pos);
            commands.add(String.format("setblock %d %d %d %s%s", pos.getX(), pos.getY(), pos.getZ(), blockStateToCommandString(state), blockEntityNbt == null ? "" : blockEntityNbt.toString()));
        }

        return commands;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String blockStateToCommandString(BlockState state) {
        List<String> properties = new ArrayList<>();
        for (Property<?> property : state.getBlock().getStateManager().getProperties()) {
            T value = (T) state.get(property);
            if (!value.equals(state.getBlock().getDefaultState().get(property))) {
                properties.add(property.getName() + "=" + ((Property<T>) property).name(value));
            }
        }
        return String.format("%s%s", Registry.BLOCK.getId(state.getBlock()), properties.isEmpty() ? "" : "[" + String.join(",", properties) + "]");
    }
}
