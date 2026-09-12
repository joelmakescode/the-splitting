package org.thesplitting.src.data.item.CollectableItems;

public class CollectableItemData {
    private final ItemCategories category;
    private int possession;

    public CollectableItemData(ItemCategories category, int possession) {
        this.category = category;
        this.possession = possession;
    }

    public ItemCategories getCategory() {
        return category;
    }

    public int getPossession() {
        return possession;
    }

    public void setPossession(int possession) {
        this.possession = possession;
    }
}
