package org.thesplitting.core.item;

import org.thesplitting.core.item.PlayerItems.StartBookItem;
import org.thesplitting.core.service.IService;
import org.thesplitting.core.service.ServiceRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemManager implements IService {
    private final ServiceRegistry registry;
    private final Map<String, IItem> items = new HashMap<>();

    public ItemManager(ServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onEnable() {
        List<Class<? extends IItem>> itemClasses = List.of(
            StartBookItem.class
        );

        itemClasses.forEach(this::register);
    }

    @Override
    public void onDisable() {

    }

    private void register(Class<? extends IItem> itemClass) {
        try {
            IItem item = itemClass.getDeclaredConstructor().newInstance();
            items.put(item.getId(), item);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            registry.getPlugin().getLogger().warning("Failed to register " + itemClass.getName() + "Error: " + e.getMessage());
        }
    }

    public IItem Item(String id) {
        return items.get(id);
    }
}
