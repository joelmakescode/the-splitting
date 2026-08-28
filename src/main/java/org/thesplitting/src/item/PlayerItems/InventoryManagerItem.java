package org.thesplitting.src.item.PlayerItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thesplitting.src.item.IItem;

public class InventoryManagerItem implements IItem {
    public static final String ID = "inventory_manager";
    public static final String NAME = "Inventory Manager";
    public static final String DISPLAY_NAME = ChatColor.GOLD + (ChatColor.ITALIC + "Inventory Manager");

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack inventoryManager = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = inventoryManager.getItemMeta();

        meta.setDisplayName(DISPLAY_NAME);

        inventoryManager.setItemMeta(meta);
        return inventoryManager;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
