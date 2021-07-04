package com.red.masaadditions.tweakeroo_additions.tweaks;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.block.Block;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PlacementTweaks {
    public static final ArrayList<Block> PERIMETER_OUTLINE_BLOCKS = new ArrayList<Block>();

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
}
