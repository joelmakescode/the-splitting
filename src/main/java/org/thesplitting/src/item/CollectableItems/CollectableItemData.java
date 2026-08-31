package org.thesplitting.src.item.CollectableItems;

public class CollectableItemData {
    private final String category;
    private int possession;

    public CollectableItemData(String category, int possession) {
        this.category = category;
        this.possession = possession;
    }

    public String getCategory() {
        return category;
    }

    public int getPossession() {
        return possession;
    }

    public void setPossession(int possession) {
        this.possession = possession;
    }
}
