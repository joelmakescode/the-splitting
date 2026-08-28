package org.thesplitting.src.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.thesplitting.src.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.services.ServiceRegistry;

public record ItemListener(ServiceRegistry registry, ItemListenerManager itemListenerManager) implements Listener, IService {
    @Override
    public void onEnable() {
        registry.getPlugin().getServer().getPluginManager().registerEvents(this, registry.getPlugin());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onItemRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) { return; }
        if (!item.hasItemMeta()) { return; }

        itemListenerManager.TakeOverItemRightClickTask(player, item);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) { return; }
        if (!event.getView().getTitle().startsWith(InventoryManagerItem.NAME)) { return; }

        itemListenerManager.TakeOverInventoryClickTask(event);
    }
}
