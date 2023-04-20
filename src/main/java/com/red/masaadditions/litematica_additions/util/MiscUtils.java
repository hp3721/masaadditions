package com.red.masaadditions.litematica_additions.util;

import com.red.masaadditions.litematica_additions.mixin.MixinFlowerPotBlockAccessor;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import net.minecraft.block.*;
import net.minecraft.block.enums.PistonType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;

import java.util.*;

public class MiscUtils {
    static final Item[] IGNORED_ITEMS_VALUES = new Item[] { Items.PISTON, Items.STICKY_PISTON, Items.WATER_BUCKET, Items.POTION, Items.LAVA_BUCKET, Items.POWDER_SNOW_BUCKET, Items.FLINT_AND_STEEL, Items.FIRE_CHARGE };
    public static final Set<Item> IGNORED_ITEMS = new HashSet<>(Arrays.asList(IGNORED_ITEMS_VALUES));

    public static class ButtonListenerOpenFolder implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            Util.getOperatingSystem().open(DataManager.getSchematicsBaseDirectory());
        }
    }

    // TODO: Refactor
    public static boolean checkHeldItem(ItemStack heldItemStack, BlockState stateSchematic) {
        Item heldItem = heldItemStack.getItem();
        boolean match = false;
        if (heldItemStack.isEmpty()) {
            return false;
        } else if (stateSchematic.getBlock() == Blocks.PISTON_HEAD || stateSchematic.getBlock() == Blocks.MOVING_PISTON) {
            match = heldItem == (stateSchematic.get(Properties.PISTON_TYPE) == PistonType.DEFAULT ? Blocks.PISTON : Blocks.STICKY_PISTON).asItem();
        } else if (stateSchematic.getFluidState().getFluid() == Fluids.WATER || stateSchematic.getFluidState().getFluid() == Fluids.FLOWING_WATER) {
            match = heldItem == Items.WATER_BUCKET;
        } else if (stateSchematic.getBlock() == Blocks.WATER_CAULDRON) {
            match = heldItem == Items.POTION && PotionUtil.getPotion(heldItemStack) == Potions.WATER || (stateSchematic.get(LeveledCauldronBlock.LEVEL) == 3 && heldItem == Items.WATER_BUCKET);
        } else if (stateSchematic.getFluidState().getFluid() == Fluids.LAVA || stateSchematic.getFluidState().getFluid() == Fluids.FLOWING_LAVA || stateSchematic.getBlock() == Blocks.LAVA_CAULDRON) {
            match = heldItem == Items.LAVA_BUCKET;
        } else if (stateSchematic.getBlock() == Blocks.POWDER_SNOW || stateSchematic.getBlock() == Blocks.POWDER_SNOW_CAULDRON) {
            match = heldItem == Items.POWDER_SNOW_BUCKET;
        } else if (stateSchematic.getBlock() == Blocks.NETHER_PORTAL) {
            match = heldItem == Items.FLINT_AND_STEEL || heldItem == Items.FIRE_CHARGE;
        } else if (stateSchematic.getBlock() instanceof FlowerPotBlock) {
            match = heldItem == ((MixinFlowerPotBlockAccessor)stateSchematic.getBlock()).getContent().asItem() || heldItem == Items.FLOWER_POT;
        } else if (stateSchematic.getBlock() instanceof Waterloggable && stateSchematic.get(Properties.WATERLOGGED)) {
            match = heldItem == Items.WATER_BUCKET;
        }
        return !match && (Item.BLOCK_ITEMS.containsValue(heldItem) || IGNORED_ITEMS.contains(heldItem)) && heldItem != stateSchematic.getBlock().asItem();
    }
}
