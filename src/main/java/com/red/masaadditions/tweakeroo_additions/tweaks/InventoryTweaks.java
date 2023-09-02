package com.red.masaadditions.tweakeroo_additions.tweaks;

import com.google.common.collect.Sets;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public class InventoryTweaks {
    private static final Set<Item> SWAP_ALMOST_BROKEN_TOOLS_WHITELIST = Sets.newIdentityHashSet();
    private static final Set<Item> SWAP_ALMOST_BROKEN_TOOLS_BLACKLIST = Sets.newIdentityHashSet();

    public static void setSwapAlmostBrokenToolsWhitelist(List<String> whitelist) {
        parseList(whitelist, SWAP_ALMOST_BROKEN_TOOLS_WHITELIST);
    }

    public static void setSwapAlmostBrokenToolsBlacklist(List<String> blacklist) {
        parseList(blacklist, SWAP_ALMOST_BROKEN_TOOLS_BLACKLIST);
    }

    private static void parseList(List<String> list, Set<Item> set) {
        set.clear();
        for (String blockStr : list) {
            Identifier id = Identifier.tryParse(blockStr);
            if (id == null) {
                continue;
            }
            Registries.ITEM.getOrEmpty(id).ifPresent(set::add);
        }
    }

    public static boolean canSwapAlmostBrokenTool(Item item) {
        if (SWAP_ALMOST_BROKEN_TOOLS_BLACKLIST.contains(item)) {
            return false;
        }
        if (!SWAP_ALMOST_BROKEN_TOOLS_WHITELIST.isEmpty()) {
            return SWAP_ALMOST_BROKEN_TOOLS_WHITELIST.contains(item);
        }
        return true;
    }
}
