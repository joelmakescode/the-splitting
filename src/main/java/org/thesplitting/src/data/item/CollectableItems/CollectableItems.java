package org.thesplitting.src.data.item.CollectableItems;

import org.thesplitting.src.data.contracts.ICollectableItem;
import org.thesplitting.src.misc.annotations.NotNull;

import java.util.*;

public class CollectableItems {
    public Map<String, CollectableItemData> items;

    public CollectableItems(Map<String, CollectableItemData> items) {
        this.items = items;
    }

    public Map<String, CollectableItemData> getCollectableItems() {
        return items;
    }

    public void addCollectableItem(@NotNull ICollectableItem collectableItem) {
        Objects.requireNonNull(collectableItem, "collectableItem is null");

        items.put(collectableItem.getId(), new CollectableItemData(collectableItem.getItemCategory(), collectableItem.getDefaultValue()));
    }

    public Integer getPossession(String itemId) {
        CollectableItemData collectableItemData = items.get(itemId);

        if (collectableItemData == null) {
            return 0;
        }

        return collectableItemData.getPossession();
    }

    public void setPossession(String itemId, Integer possession) {
        CollectableItemData collectableItemData = items.get(itemId);

        if (collectableItemData == null) {
           return;
        }

        collectableItemData.setPossession(possession);
    }
}
