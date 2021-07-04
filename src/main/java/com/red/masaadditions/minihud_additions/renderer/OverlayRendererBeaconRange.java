package com.red.masaadditions.minihud_additions.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.red.masaadditions.minihud_additions.config.ConfigsExtended;
import com.red.masaadditions.minihud_additions.config.RendererToggleExtended;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.minihud.renderer.OverlayRendererBase;
import fi.dy.masa.minihud.renderer.RenderObjectBase;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.world.Heightmap.Type.WORLD_SURFACE;

// From 1.12 MiniHUD by Masa
public class OverlayRendererBeaconRange extends OverlayRendererBase {
    private static final Set<BlockPos> BEACON_POSITIONS = new HashSet<>();
    private static final Set<ChunkPos> BEACON_CHUNKS = new HashSet<>();

    private static boolean needsUpdate;

    public static void clear() {
        synchronized (BEACON_POSITIONS) {
            BEACON_CHUNKS.clear();
            BEACON_POSITIONS.clear();
        }
    }

    public static void setNeedsUpdate() {
        if (!RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue()) {
            clear();
        }

        needsUpdate = true;
    }

    public static void checkNeedsUpdate(BlockPos pos, BlockState state) {
        synchronized (BEACON_POSITIONS) {
            if (RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue() &&
                    (state.getBlock() instanceof BeaconBlock || BEACON_POSITIONS.contains(pos))) {
                setNeedsUpdate();
            }
        }
    }

    public static void checkNeedsUpdate(ChunkPos chunkPos) {
        synchronized (BEACON_POSITIONS) {
            if (RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue() &&
                    BEACON_CHUNKS.contains(chunkPos)) {
                setNeedsUpdate();
            }
        }
    }

    @Override
    public boolean shouldRender(MinecraftClient mc) {
        return RendererToggleExtended.OVERLAY_BEACON_RANGE.getBooleanValue();
    }

    @Override
    public boolean needsUpdate(Entity entity, MinecraftClient mc) {
        if (needsUpdate || this.lastUpdatePos == null) {
            return true;
        }

        return false;
    }

    @Override
    public void update(Vec3d cameraPos, Entity entity, MinecraftClient mc) {
        clear();

        RenderObjectBase renderQuads = this.renderObjects.get(0);
        RenderObjectBase renderLines = this.renderObjects.get(1);
        BUFFER_1.begin(renderQuads.getGlMode(), VertexFormats.POSITION_COLOR);
        BUFFER_2.begin(renderLines.getGlMode(), VertexFormats.POSITION_COLOR);

        synchronized (BEACON_POSITIONS) {
            this.renderBeaconRanges(entity.getEntityWorld(), cameraPos, BUFFER_1, BUFFER_2);
        }

        BUFFER_1.end();
        BUFFER_2.end();
        renderQuads.uploadData(BUFFER_1);
        renderLines.uploadData(BUFFER_2);

        needsUpdate = false;
    }

    @Override
    public void allocateGlResources() {
        this.allocateBuffer(GL11.GL_QUADS);
        this.allocateBuffer(GL11.GL_LINES);
    }

    protected static Color4f getColorForLevel(int level) {
        switch (level) {
            case 1:
                return ConfigsExtended.Colors.BEACON_RANGE_LVL1_OVERLAY_COLOR.getColor();
            case 2:
                return ConfigsExtended.Colors.BEACON_RANGE_LVL2_OVERLAY_COLOR.getColor();
            case 3:
                return ConfigsExtended.Colors.BEACON_RANGE_LVL3_OVERLAY_COLOR.getColor();
            default:
                return ConfigsExtended.Colors.BEACON_RANGE_LVL4_OVERLAY_COLOR.getColor();
        }
    }

    protected void renderBeaconRanges(World world, Vec3d cameraPos, BufferBuilder bufferQuads, BufferBuilder bufferLines) {
        for (BlockEntity be : world.blockEntities) {
            if (be instanceof BeaconBlockEntity) {
                BlockPos pos = be.getPos();
                int level = ((BeaconBlockEntity) be).getLevel();

                if (level >= 1 && level <= 4) {
                    this.renderBeaconBox(world, pos, cameraPos, level, getColorForLevel(level), bufferQuads, bufferLines);
                }
            }
        }
    }

    protected void renderBeaconBox(World world, BlockPos pos, Vec3d cameraPos, int level, Color4f color, BufferBuilder bufferQuads, BufferBuilder bufferLines) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        int range = level * 10 + 10;
        double minX = x - range - cameraPos.x;
        double minY = y - range - cameraPos.y;
        double minZ = z - range - cameraPos.z;
        double maxX = x + range + 1 - cameraPos.x;
        double maxY = this.getMaxHeight(world, pos, range) - cameraPos.y;
        double maxZ = z + range + 1 - cameraPos.z;

        fi.dy.masa.malilib.render.RenderUtils.drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, color, bufferQuads);
        fi.dy.masa.malilib.render.RenderUtils.drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, Color4f.fromColor(color, 1f), bufferLines);

        BEACON_POSITIONS.add(pos);
        BEACON_CHUNKS.add(new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4));
    }

    protected int getMaxHeight(World world, BlockPos pos, int range) {
        final int minX = pos.getX() - range;
        final int minZ = pos.getZ() - range;
        final int maxX = pos.getX() + range;
        final int maxZ = pos.getZ() + range;

        final int minCX = minX >> 4;
        final int minCZ = minZ >> 4;
        final int maxCX = maxX >> 4;
        final int maxCZ = maxZ >> 4;
        int maxY = 0;

        for (int cz = minCZ; cz <= maxCZ; ++cz) {
            for (int cx = minCX; cx <= maxCX; ++cx) {
                final int xMin = Math.max(minX, cx << 4);
                final int zMin = Math.max(minZ, cz << 4);
                final int xMax = Math.min(maxX, (cx << 4) + 15);
                final int zMax = Math.min(maxZ, (cz << 4) + 15);
                Chunk chunk = world.getChunk(cx, cz);

                for (int z = zMin; z <= zMax; ++z) {
                    for (int x = xMin; x <= xMax; ++x) {
                        int height = chunk.sampleHeightmap(WORLD_SURFACE, x, z);

                        if (height > maxY) {
                            maxY = height;
                        }
                    }
                }
            }
        }

        return maxY + 4;
    }

    public static void renderBeaconBoxForPlayerIfHoldingItem(PlayerEntity player, double dx, double dy, double dz, float partialTicks) {
        Item item = player.getMainHandStack().getItem();

        if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof BeaconBlock) {
            renderBeaconBoxForPlayer(player, dx, dy, dz, partialTicks);
            return;
        }

        item = player.getOffHandStack().getItem();

        if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof BeaconBlock) {
            renderBeaconBoxForPlayer(player, dx, dy, dz, partialTicks);
            return;
        }
    }

    private static void renderBeaconBoxForPlayer(PlayerEntity player, double dx, double dy, double dz, float partialTicks) {
        double x = Math.floor(player.getX()) - dx;
        double y = Math.floor(player.getY()) - dy;
        double z = Math.floor(player.getZ()) - dz;
        // Use the slot number as the level if sneaking
        int level = player.isSneaking() ? Math.min(4, player.inventory.selectedSlot + 1) : 4;
        double range = level * 10 + 10;
        double minX = x - range;
        double minY = y - range;
        double minZ = z - range;
        double maxX = x + range + 1;
        double maxY = y + 4;
        double maxZ = z + range + 1;
        Color4f color = getColorForLevel(level);

        GlStateManager.disableTexture();
        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01F);
        GlStateManager.disableCull();
        GlStateManager.disableLighting();
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.polygonOffset(-3f, -3f);
        GlStateManager.enablePolygonOffset();
        GlStateManager.lineWidth(1f);
        fi.dy.masa.malilib.render.RenderUtils.setupBlend();
        fi.dy.masa.malilib.render.RenderUtils.color(1f, 1f, 1f, 1f);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);

        fi.dy.masa.malilib.render.RenderUtils.drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, Color4f.fromColor(color, 0.3f), buffer);

        tessellator.draw();
        buffer.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);

        fi.dy.masa.malilib.render.RenderUtils.drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, Color4f.fromColor(color, 1f), buffer);

        tessellator.draw();

        GlStateManager.polygonOffset(0f, 0f);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableCull();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
}