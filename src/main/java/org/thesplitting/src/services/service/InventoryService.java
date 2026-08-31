package org.thesplitting.src.services.service;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.thesplitting.src.entities.player.playerdata.PlayerData;
import org.thesplitting.src.events.item.GenericItemGenerator;
import org.thesplitting.src.item.CollectableItems.CollectableItemData;
import org.thesplitting.src.item.CollectableItems.ItemCategories;
import org.thesplitting.src.item.IItem;
import org.thesplitting.src.item.ItemManager;
import org.thesplitting.src.item.PlayerItems.InventoryManagerItem;
import org.thesplitting.src.services.IService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public record InventoryService(ItemManager itemManager, PlayerService playerService) implements IService {
    static GenericItemGenerator genericItemGenerator = new GenericItemGenerator();
    private static final Map<UUID, List<List<String>>> playerPageCache = new HashMap<>();
    private final static int PAGE_SIZE = 45;

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void openInventoryManager(Player player) {
        PlayerData playerData = playerService.loadPlayerFile(player);
        List<List<String>> pages = buildPaginatedItems(playerData);

        playerPageCache.put(player.getUniqueId(), pages);
        renderPage(player, pages, 1);
    }

    public void nextInventoryPage(Player player) {
        List<List<String>> pages = playerPageCache.get(player.getUniqueId());
        if (pages == null) { openInventoryManager(player); return; }
        int currentPage = getCurrentPage(player.getOpenInventory().getTitle());
        renderPage(player, pages, currentPage + 1);
    }

    public void lastInventoryPage(Player player) {
        List<List<String>> pages = playerPageCache.get(player.getUniqueId());
        if (pages == null) { openInventoryManager(player); return; }
        int currentPage = getCurrentPage(player.getOpenInventory().getTitle());
        renderPage(player, pages, currentPage - 1);
    }

    private void renderPage(Player player, List<List<String>> pages, int page) {
        if (pages.isEmpty()) {
            player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.RED + "There are no items in your inventory yet.");
            return;
        }

        int clampedPage = Math.max(1, Math.min(page, pages.size()));
        List<String> items = pages.get(clampedPage - 1);

        Inventory inventory = Bukkit.createInventory(null, 54, InventoryManagerItem.NAME + " (Page: " + clampedPage + ")");
        inventory.setItem(45, genericItemGenerator.getItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "Back"));
        inventory.setItem(53, genericItemGenerator.getItem(Material.RED_STAINED_GLASS_PANE, "Next"));

        int slot = 0;
        for (String itemId : items) {
            IItem item = itemManager.Item(itemId);
            if (item == null) { continue; }
            inventory.setItem(slot++, item.getItemStack());
        }

        player.openInventory(inventory);
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


    private List<List<String>> buildPaginatedItems(@NotNull PlayerData playerData) {
        Map<String, CollectableItemData> collectables = playerData.collectableItems().getCollectableItems();
        Set<String> equipped = Arrays.stream(playerData.playerInventory().inventorySlots())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<List<String>> pages = new ArrayList<>();

        for (ItemCategories category : ItemCategories.values()) {
            List<String> categoryItems = collectables.entrySet().stream()
                    .filter(e -> category.toString().equals(e.getValue().getCategory()))
                    .filter(e -> e.getValue().getPossession() > 0)
                    .filter(e -> e.getKey() != null && !equipped.contains(e.getKey()))
                    .map(Map.Entry::getKey)
                    .sorted()
                    .toList();

            if (categoryItems.isEmpty()) {
                continue;
            }

            for (int i = 0; i < categoryItems.size(); i+= PAGE_SIZE) {
                pages.add(new ArrayList<>(categoryItems.subList(i, Math.min(i + PAGE_SIZE, categoryItems.size()))));
            }
        }

        return pages;
    }
}
