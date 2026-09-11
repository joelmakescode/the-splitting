package org.thesplitting.src.data.player;

import org.thesplitting.src.data.item.CollectableItems.CollectableItems;
import org.thesplitting.src.data.player.PlayerRoles.PlayerRole;
import org.thesplitting.src.data.player.PlayerRoles.PlayerRoles;

public record PlayerData(String name, PlayerRole playerRole, PlayerSettings playerSettings, PlayerStats playerStats, PlayerInventory playerInventory, CollectableItems collectableItems) {

    // TODO: LOOK ON THIS SCHEMA LATER, PLAYERDEFAULTDATA MIGHT BE OBSOLETE
    public static PlayerData create(String playerName) {
        return new PlayerData(
                playerName,
                new PlayerRole(PlayerRoles.NOVICE.toString()),
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
