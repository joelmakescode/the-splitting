package org.thesplitting.src.data.contracts;

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
