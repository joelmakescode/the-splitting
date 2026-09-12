package org.thesplitting.src.data.item.CollectableItems;

public enum ItemCategories {
    MELEE(0, "Melee"),
    BOW(1, "Bow"),
    POTION(2, "Potion"),
    MISCELLANEOUS(3, "Miscellaneous");

    private final int id;
    private final String label;

    ItemCategories(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }
    public String getLabel() {
        return label;
    }
}
