package org.thesplitting.src.item.CollectableItems;

import org.thesplitting.src.item.IItem;

public interface ICollectableItem extends IItem {
    /**
     * Returns the category of an item
     * @return category
     */
    String getCategory();

    /**
     * Default value for the possession of this item.
     * @return value (0 default)
     */
    default int getDefaultValue() {
        return 0;
    }
}
