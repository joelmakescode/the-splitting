package org.thesplitting.src.services.services;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.thesplitting.src.misc.errorhandler.PlayerErrorHandler;
import org.thesplitting.src.services.contracts.IService;
import org.thesplitting.src.data.player.PlayerData;
import org.thesplitting.src.services.services.fileservice.PlayerFileService;
import org.thesplitting.src.misc.exceptions.PlayerDataNotFoundException;
import org.thesplitting.src.services.services.itemservice.ItemService;

import java.util.Objects;

public record PlayerService(PlayerFileService playerFileManager, ItemService itemManager) implements IService {
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void initiatePlayerSetup(Player player) {
        PlayerData playerData = loadPlayerFile(player);
        loadPlayer(playerData, player);

        if (playerData.playerSettings().getIsStarter() == 1) {
            playerData.playerSettings().setIsStarter(0);
        }

        playerFileManager.writePlayerFile(player, playerData);
    }

    public PlayerData loadPlayerFile(Player player) {
        PlayerData playerData = playerFileManager.readPlayerFile(player);
        if (playerData == null) {
            playerFileManager.createPlayerFile(player);

            return loadPlayerFile(player);
        }

        return playerData;
    }

    public void savePlayerFile(Player player) {
        PlayerData playerData = loadPlayerFile(player);
        playerFileManager.writePlayerFile(player, playerData);
    }

    public void updatePlayerFile(Player player, PlayerData playerData) {
        playerFileManager.writePlayerFile(player, playerData);
    }

    private void loadPlayer(PlayerData playerData, Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setMaxHealth(playerData.playerStats().getMaxHealth());
        player.setLevel(playerData.playerStats().getLevel());

        loadInventory(playerData, player);
    }

    private void loadInventory(PlayerData playerData, Player player) {
        String[] currentInventorySlots = playerData.playerInventory().inventorySlots();
        for (int i = 0; i < currentInventorySlots.length; i++) {
            String key = currentInventorySlots[i];
            if (key == null || key.isEmpty()) {
                continue;
            }
            Objects.requireNonNull(player.getPlayer()).getInventory().setItem(i, itemManager.getItemStack(key));
        }
    }
}
