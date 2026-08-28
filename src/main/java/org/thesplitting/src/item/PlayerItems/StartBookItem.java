package org.thesplitting.src.item.PlayerItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.thesplitting.src.item.IItem;

public class StartBookItem implements IItem {
    public static final String ID = "start_book";
    public static final String NAME = "Instructions";
    public static final String DISPLAY_NAME = ChatColor.AQUA + NAME;

    @Override
    public String getId() {
       return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(DISPLAY_NAME);
            meta.setTitle(ChatColor.AQUA + "Willkommen");
            meta.setAuthor(ChatColor.AQUA + "The Splitting");

            meta.addPage(
                    "Willkommen auf diesem Server! \n\n",
                    "Dieses Buch dient als einsteigende Einleitung für diesen Server."
            );

            book.setItemMeta(meta);
        }

        return book;
    }
}
