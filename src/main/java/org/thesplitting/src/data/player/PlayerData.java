package org.thesplitting.src.data.player;

import org.thesplitting.src.data.item.CollectableItems.CollectableItems;

public record PlayerData(String name, PlayerSettings playerSettings, PlayerStats playerStats, PlayerInventory playerInventory, CollectableItems collectableItems) {

    public static PlayerData create(String playerName) {
        return new PlayerData(
                playerName,
                new PlayerSettings(PlayerDefaultData.SETTINGS.getIsStarter()),
                new PlayerStats(
                        PlayerDefaultData.STATS.getMaxHealth(),
                        PlayerDefaultData.STATS.getLevel(),
                        PlayerDefaultData.STATS.getInferno(),
                        PlayerDefaultData.STATS.getBolt(),
                        PlayerDefaultData.STATS.getWater_flow(),
                        PlayerDefaultData.STATS.getGreen_flame()
                ),
                new PlayerInventory(PlayerDefaultData.INVENTORY.inventorySlots()),
                new CollectableItems(PlayerDefaultData.COLLECTABLES.getCollectableItems())
        );
    }
}
