package org.thesplitting.src.entities.player.playerdata;

import org.thesplitting.src.item.CollectableItems.CollectableItems;
import org.thesplitting.src.item.CollectableItems.ICollectableItem;
import org.thesplitting.src.item.ItemManager;
import org.thesplitting.src.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.item.PlayerItems.StartBookItem;
import org.thesplitting.src.item.PlayerItems.StartSwordItem;

import java.util.HashMap;

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

        for (ICollectableItem item : ItemManager.getItems()) {
            collectableItems.addCollectableItem(item);
        }

        return collectableItems;
    }
}
