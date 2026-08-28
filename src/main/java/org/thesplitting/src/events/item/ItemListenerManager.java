package org.thesplitting.src.events.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.thesplitting.src.entities.player.playerdata.PlayerData;
import org.thesplitting.src.item.CollectableItems;
import org.thesplitting.src.item.ItemManager;
import org.thesplitting.src.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.services.service.PlayerService;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemListenerManager implements IService {
    GenericItemGenerator genericItemGenerator = new GenericItemGenerator();
    private final PlayerService playerService;
    private final ItemManager itemManager;

    public ItemListenerManager(PlayerService playerService, ItemManager itemManager) {
        this.playerService = playerService;
        this.itemManager = itemManager;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void TakeOverItemRightClickTask(Player player, ItemStack itemStack) {
        if (Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName().equals(InventoryManagerItem.DISPLAY_NAME)) {
            takeOverInventoryManagerTask(player, 1);
        }
    }

    public void TakeOverInventoryClickTask(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getRawSlot() == 53) {
            nextInventoryPage(player);
        } else if (event.getRawSlot() == 45) {
            lastInventoryPage(player);
        }
    }

    private void takeOverInventoryManagerTask(Player player, Integer currentPage) {
        Inventory inventory = Bukkit.createInventory(null, 54, InventoryManagerItem.NAME + " (Page: " + currentPage + ")");

        inventory.setItem(45, genericItemGenerator.getItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "Back"));
        inventory.setItem(53, genericItemGenerator.getItem(Material.RED_STAINED_GLASS_PANE, "Next"));

        PlayerData playerData = playerService.loadPlayerFile(player);
        fillPlayerInventory(inventory, playerData);

        player.openInventory(inventory);
    }

    private void nextInventoryPage(Player player) {
        String inventoryName = player.getOpenInventory().getTitle();
        int currentPage = getCurrentPage(inventoryName);
        int nextPage = 1;

        takeOverInventoryManagerTask(player, currentPage + 1);
    }

    private void lastInventoryPage(Player player) {
        String inventoryName = player.getOpenInventory().getTitle();
        int currentPage = getCurrentPage(inventoryName);

        int previousPage = Math.max(1, currentPage - 1);

        takeOverInventoryManagerTask(player, previousPage);
    }

    private int getCurrentPage(String inventoryName) {
        if (inventoryName == null || inventoryName.isEmpty()) {
            return 1;
        }

        Pattern pattern = Pattern.compile("\\(Page:\\s*(\\d+)\\)");
        Matcher matcher = pattern.matcher(inventoryName);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 1;
    }

    private void fillPlayerInventory(Inventory inventory, @NotNull PlayerData playerData) {
        CollectableItems collectableItems = playerData.collectableItems();
        HashMap<String, Integer> collectables = collectableItems.getCollectableItems();

        int slot = 0;

        for (String key : collectables.keySet()) {
            if (key == null || key.isEmpty()) {
                continue;
            }

            inventory.setItem(slot++, itemManager.Item(key).getItemStack());

            if (slot >= 45) {
                break;
            }
        }
    }
}
