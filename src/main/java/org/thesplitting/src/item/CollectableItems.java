package org.thesplitting.src.item;

import org.thesplitting.src.misc.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class CollectableItems {
    HashMap<String, Integer> items = new HashMap<>();

    public CollectableItems() {

    }

    public CollectableItems(HashMap<String, Integer> collectableItems) {
        items = collectableItems;
    }

    public HashMap<String, Integer> getCollectableItems() {
        return items;
    }

    public Integer getCollectableItem(String key) {
        return items.get(key);
    }

    public void addCollectableItem(@NotNull String key, @NotNull Integer value) {
        Objects.requireNonNull(key, "key is null");
        Objects.requireNonNull(value, "value is null");

        items.put(key, value);
    }
}
