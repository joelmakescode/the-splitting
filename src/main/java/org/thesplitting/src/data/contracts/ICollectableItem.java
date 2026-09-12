package org.thesplitting.src.data.contracts;

import org.thesplitting.src.data.item.CollectableItems.ItemCategories;
import org.thesplitting.src.data.item.CollectableItems.ItemInventorySlots;

public interface ICollectableItem extends IItem {
    /**
     * Returns the category of an item
     * @return category
     */
    default ItemCategories getItemCategory() {
        return ItemCategories.MISCELLANEOUS;
    };

    /**
     * Default value for the possession of this item.
     * @return value (0 default)
     */
    default int getDefaultValue() {
        return 0;
    }

    /**
     * Inventory slot this item may be equipped in.
     * @return slot restriction (NONE by default)
     */
    default ItemInventorySlots getInventorySlot() {
        return ItemInventorySlots.NONE;
    }
}
