package org.thesplitting.core.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.thesplitting.core.errorhandler.PlayerErrorHandler;
import org.thesplitting.core.item.CustomItem;
import org.thesplitting.core.item.ItemManager;
import org.thesplitting.core.service.IService;
import org.thesplitting.data.filemanager.PlayerFileManager;
import org.thesplitting.exceptions.PlayerDataNotFoundException;

public class PlayerService implements IService {
    private final PlayerFileManager playerFileManager;
    private final ItemManager itemManager;
    private final PlayerErrorHandler playerErrorHandler;

    public PlayerService(PlayerFileManager playerFileManager, ItemManager itemManager, PlayerErrorHandler playerErrorHandler) {
        this.playerFileManager = playerFileManager;
        this.itemManager = itemManager;
        this.playerErrorHandler = playerErrorHandler;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void increasePlayerLevel(Player player) {
        PlayerData playerData = getPlayerData(player);

        playerData.setLevel(playerData.getLevel() + 1);
        playerFileManager.writePlayerFile(player, playerData);
    }

    public void initiatePlayerSetup(Player player) {
        PlayerData playerData = getPlayerData(player);

        player.setGameMode(GameMode.SURVIVAL);
        player.setMaxHealth(playerData.getMaxHealth());
        player.getInventory().clear();
        player.getInventory().setItem(8, itemManager.Item(CustomItem.START_BOOK.getId()).getItemStack());
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

        playerData.setLevel(newLevel);
        playerFileManager.writePlayerFile(player, playerData);
    }

    private PlayerData getPlayerData(Player player) {
        PlayerData playerData = playerFileManager.readPlayerFile(player);
        if (playerData == null) {
            playerErrorHandler.playerDataNotFoundError(player);
            throw new PlayerDataNotFoundException(player.getName());
        }
        return playerData;
    }
}
