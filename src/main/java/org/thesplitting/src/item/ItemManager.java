package org.thesplitting.src.item;

import org.thesplitting.src.item.CollectableItems.ICollectableItem;
import org.thesplitting.src.item.CollectableItems.Items.FireSword;
import org.thesplitting.src.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.item.PlayerItems.StartBookItem;
import org.thesplitting.src.item.PlayerItems.StartSwordItem;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.services.ServiceRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemManager implements IService {
    private final ServiceRegistry registry;
    private final static List<IItem> ITEM_INSTANCES = List.of(
            new StartBookItem(),
            new StartSwordItem(),
            new InventoryManagerItem(),

            new FireSword()
    );
    private final Map<String, IItem> items = new HashMap<>();

    public ItemManager(ServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onEnable() {
        ITEM_INSTANCES.forEach(item -> items.put(item.getId(), item));
    }

    @Override
    public void onDisable() {

    }

    public IItem Item(String id) {
        return items.get(id);
    }

    public static List<ICollectableItem> getItems() {
        return ITEM_INSTANCES.stream()
                .filter(ICollectableItem.class::isInstance)
                .map(ICollectableItem.class::cast)
                .toList();
    }
}
