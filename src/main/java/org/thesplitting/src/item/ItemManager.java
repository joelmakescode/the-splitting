package org.thesplitting.src.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
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
    private NamespacedKey idKey;
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
        idKey = new NamespacedKey(registry.getPlugin(), "item_id");
        ITEM_INSTANCES.forEach(item -> items.put(item.getId(), item));
    }

    @Override
    public void onDisable() {

    }

    public static List<ICollectableItem> getItems() {
        return ITEM_INSTANCES.stream()
                .filter(ICollectableItem.class::isInstance)
                .map(ICollectableItem.class::cast)
                .toList();
    }

    public ItemStack getItemStack(String id) {
        IItem item = items.get(id);
        if (item == null) return null;

        ItemStack stack = item.getItemStack();
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public String resolveId(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) return null;
        return stack.getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
    }
}
