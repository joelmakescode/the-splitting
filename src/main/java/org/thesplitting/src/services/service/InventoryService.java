package org.thesplitting.src.services.service;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
    static HashMap<Integer, String> INVENTORY_TILES = new HashMap<>(){
        {
            put(0, "MELEE");
            put(1, "BOW");
            put(2, "POTION 1");
            put(3, "POTION 2");
        }
    };

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

    public void openItemSwitcher(InventoryClickEvent event, Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Item Switcher");
        inventory.setItem(4, event.getCurrentItem());
        loadIndividualInventory(player, inventory, 9);
    }

    public void openItemRemover(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 18, "Item Remover");
        loadIndividualInventory(player, inventory, 0);
    }

    public void itemSwitcherAction(InventoryClickEvent event, Player player) {
        if (event.getClickedInventory() == null) { return; }
        if (event.getRawSlot() == 22) {
            openInventoryManager(player);
        } else if (event.getRawSlot() >= 9 && event.getRawSlot() <= 12) {
            int slot = event.getRawSlot() - 9;
            ItemStack chosenItem = player.getOpenInventory().getItem(4);
            ItemStack itemToSwitch = player.getInventory().getItem(slot);

            if (!chosenItem.getItemMeta().getLore().contains(INVENTORY_TILES.get(slot))) {
                player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.YELLOW + "Inventory slot is reserved for: " + formatWord(slot));
                return;
            }

            if (chosenItem.equals(itemToSwitch)) {
                openInventoryManager(player);
                player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.YELLOW + "Can't switch to a weapon that is already in use.");
                return;
            }

            if (!player.getInventory().contains(chosenItem)) {
                if (itemToSwitch != null && slot != 4) {
                    player.getInventory().removeItem(itemToSwitch);
                }
                player.getInventory().setItem(slot, chosenItem);

                PlayerData playerData = playerService.loadPlayerFile(player);
                String itemId = itemManager.resolveId(chosenItem);
                playerData.playerInventory().setInventorySlot(slot, itemId);

                playerService.updatePlayerFile(player, playerData);
                openInventoryManager(player);
                player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.GREEN + "Item switch successful!");
            } else {
                player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.RED + "This item is already in your inventory.");
            }
        }
    }

    public void itemRemoverAction(InventoryClickEvent event, Player player) {
        if (event.getClickedInventory() == null) { return; }
        ItemStack itemToRemove = event.getCurrentItem();
        PlayerData playerData = playerService.loadPlayerFile(player);

        if (event.getRawSlot() == 13) {
            openInventoryManager(player);
            return;
        }

        if (itemToRemove == null) { return; }
        if (itemToRemove.getItemMeta().getDisplayName().contains("Slot")) {
            player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.YELLOW + "You cannot remove an empty slot.");
            return;
        }

        // REFACTOR THIS LATER
        if (event.getClickedInventory().contains(Material.NETHER_STAR)) {
            player.sendMessage(ChatColor.GOLD + "[The Splitting] " +  ChatColor.RED + "This is not allowed.");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        if (inventory.contains(itemToRemove)) {
            player.sendMessage(ChatColor.GOLD + "[The Splitting] " + ChatColor.GREEN + "Item successfully removed.");
            inventory.remove(itemToRemove);
            player.closeInventory();
            playerData.playerInventory().setInventorySlot(event.getRawSlot() % 9, null);
            playerService.updatePlayerFile(player, playerData);
        }
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
        inventory.setItem(49, genericItemGenerator.getItem(Material.BARRIER, "Item Remover"));
        inventory.setItem(53, genericItemGenerator.getItem(Material.RED_STAINED_GLASS_PANE, "Next"));

        int slot = 0;
        for (String itemId : items) {
            inventory.setItem(slot++, itemManager.getItemStack(itemId));
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

    /**
     * Builds every page dedicated to the individual user.
     * @param playerData
     * @return Pages
     */
    private List<List<String>> buildPaginatedItems(@NotNull PlayerData playerData) {
        Map<String, CollectableItemData> collectables = playerData.collectableItems().getCollectableItems();

        List<List<String>> pages = new ArrayList<>();

        for (ItemCategories category : ItemCategories.values()) {
            List<String> categoryItems = collectables.entrySet().stream()
                    .filter(e -> category.toString().equals(e.getValue().getCategory()))
                    .filter(e -> e.getValue().getPossession() > 0)
                    .filter(e -> e.getKey() != null)
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

    /**
     * Loads the individual inventory based on the action before. (Including Item Switcher; Item Remover;)
     * @param player
     * @param inventory
     * @param startSlot
     */
    private void loadIndividualInventory(Player player, Inventory inventory, int startSlot) {
        PlayerInventory playerInventory = player.getInventory();

        for (int i = startSlot; i <= startSlot + 3; i++) {
            ItemStack currentItem = playerInventory.getItem(i % 9);
            if (currentItem != null) {
                inventory.setItem(i, currentItem);
                continue;
            }

            inventory.setItem(i, genericItemGenerator.getItem(Material.COOKIE, "Slot: " + ((i % 9) + 1) + " (Reserved for " + formatWord(i % 9) + ")"));
        }

        for (int i = startSlot + 5; i <= startSlot + 8; i++) {
            inventory.setItem(i, genericItemGenerator.getItem(Material.BARRIER, "Not allowed"));
        }

        inventory.setItem(startSlot + 13, genericItemGenerator.getItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "Back"));
        player.openInventory(inventory);
    }

    /**
     * Formats the word for the reserved slot.
     * @param slot
     * @return formattedWord
     */
    private String formatWord(int slot) {
        String reservedFor = INVENTORY_TILES.get(slot);
        return Character.toUpperCase(reservedFor.charAt(0)) + reservedFor.substring(1).toLowerCase();
    }
}
