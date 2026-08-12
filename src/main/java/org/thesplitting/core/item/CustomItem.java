package org.thesplitting.core.item;

public enum CustomItem {
    START_BOOK("start_book");

    private final String id;

    CustomItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
