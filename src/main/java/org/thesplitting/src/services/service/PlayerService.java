package org.thesplitting.src.services.service;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.thesplitting.src.misc.errorhandler.PlayerErrorHandler;
import org.thesplitting.src.item.ItemManager;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.entities.player.playerdata.PlayerData;
import org.thesplitting.src.filemanager.PlayerFileManager;
import org.thesplitting.src.misc.exceptions.PlayerDataNotFoundException;

import java.util.Objects;

public record PlayerService(PlayerFileManager playerFileManager, ItemManager itemManager, PlayerErrorHandler playerErrorHandler) implements IService {
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void increasePlayerLevel(Player player) {
        PlayerData playerData = getPlayerData(player);

        playerData.playerStats().setLevel(playerData.playerStats().getLevel() + 1);
        playerFileManager.writePlayerFile(player, playerData);
    }

    public void initiatePlayerSetup(Player player) {
        PlayerData playerData = getPlayerData(player);
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
        PlayerData playerData = getPlayerData(player);
        playerFileManager.writePlayerFile(player, playerData);
    }

    public void setPlayerLevel(Player player, int newLevel) {
        PlayerData playerData = getPlayerData(player);

        playerData.playerStats().setLevel(newLevel);
        playerFileManager.writePlayerFile(player, playerData);
    }

    private PlayerData getPlayerData(Player player) {
        PlayerData playerData = playerFileManager.readPlayerFile(player);
        if (playerData == null) {
            playerErrorHandler.playerDataNotFoundError(player);
            loadPlayerFile(player);
            throw new PlayerDataNotFoundException(player.getName());
        }
        return playerData;
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
            Objects.requireNonNull(player.getPlayer()).getInventory().setItem(i, itemManager.Item(currentInventorySlots[i]).getItemStack());
        }
    }
}
