package org.thesplitting.core.item.PlayerItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thesplitting.core.item.IItem;

public class StartBookItem implements IItem {

    public StartBookItem() {
    }

    @Override
    public String getId() {
       return "start_book";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta meta = book.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + getName());
            book.setItemMeta(meta);
        }

        return book;
    }

    @Override
    public String getName() {
        return "Instructions";
    }

    @Override
    public void setName(String name) {

    }
}
