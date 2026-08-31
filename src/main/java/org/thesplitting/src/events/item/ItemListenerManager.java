package org.thesplitting.src.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.thesplitting.src.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.services.service.InventoryService;

import java.util.Objects;

public record ItemListenerManager(InventoryService inventoryService) implements IService {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void TakeOverItemRightClickTask(Player player, ItemStack itemStack) {
        if (Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName().equals(InventoryManagerItem.DISPLAY_NAME)) {
            inventoryService.openInventoryManager(player);
        }
    }

    public void TakeOverInventoryClickTask(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getRawSlot() == 53) {
            inventoryService.nextInventoryPage(player);
        } else if (event.getRawSlot() == 45) {
            inventoryService.lastInventoryPage(player);
        }
    }
}
