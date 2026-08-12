package org.thesplitting.core.item;

import org.bukkit.inventory.ItemStack;

public interface IItem {

    /**
     * Returns the item id.
     * @return id
     */
    String getId();

    /**
     * Returns the item.
     * @return item
     */
    ItemStack getItemStack();

    /**
     * Returns the name of an item.
     * @return name of the item
     */
    String getName();

    /**
     * Sets the name of an item.
     * @param name
     */
    void setName(String name);
}
