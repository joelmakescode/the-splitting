package org.thesplitting.src.item;

import org.bukkit.inventory.ItemStack;

public interface IItem {

    /**
     * Returns the item id.
     * @return id
     */
    String getId();

    /**
     * Returns the name of an item.
     * @return name
     */
    String getName();

    /**
     * Returns the item.
     * @return item
     */
    ItemStack getItemStack();
}
