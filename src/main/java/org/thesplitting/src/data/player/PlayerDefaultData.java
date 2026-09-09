package org.thesplitting.src.data.player;

import org.thesplitting.src.data.item.CollectableItems.CollectableItems;
import org.thesplitting.src.data.contracts.ICollectableItem;
import org.thesplitting.src.services.services.itemservice.ItemService;
import org.thesplitting.src.data.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.data.item.PlayerItems.StartBookItem;
import org.thesplitting.src.data.item.PlayerItems.StartSwordItem;

import java.util.HashMap;

import static org.thesplitting.src.services.services.itemservice.ItemService.getItems;

public class PlayerDefaultData {
    private PlayerDefaultData() {}

    public static final PlayerSettings SETTINGS = new PlayerSettings(1);

    public static final PlayerStats STATS = new PlayerStats(4, 1, 1, 1, 1, 1);

    public static final PlayerInventory INVENTORY = new PlayerInventory(new String[]{
            StartSwordItem.ID,
            null,
            null,
            null,
            InventoryManagerItem.ID,
            null,
            null,
            null,
            StartBookItem.ID
    });

    public static final CollectableItems COLLECTABLES = createDefaultCollectableItems();

    private static CollectableItems createDefaultCollectableItems() {
        CollectableItems collectableItems = new CollectableItems(new HashMap<>());
        getItems().forEach(collectableItems::addCollectableItem);
        return collectableItems;
    }
}
