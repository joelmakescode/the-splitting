package org.thesplitting.data.filemanager;

import org.bukkit.entity.Player;
import org.thesplitting.core.player.PlayerData;
import org.thesplitting.core.player.PlayerDefaultData;
import org.thesplitting.core.service.ServiceRegistry;

import java.nio.file.Path;

public class PlayerFileManager extends FileManager {
    protected final Path folderName;
    PlayerDefaultData playerDefaultData = PlayerDefaultData.DEFAULT;

    public PlayerFileManager(ServiceRegistry registry, Path folderName) {
        super(registry, folderName);
        this.folderName = folderName;
    }

    public void createPlayerFile(Player player) {
        PlayerData playerData = new PlayerData(
            player.getName(),
            playerDefaultData.defaultMaxHealth(),
            playerDefaultData.defaultLevel()
        );

        super.jsonFileCreate(folderName, player.getUniqueId().toString(), playerData);
    }

    public PlayerData readPlayerFile(Player player) {
        return super.jsonFileRead(folderName, player.getUniqueId().toString(), PlayerData.class);
    }

    public void writePlayerFile(Player player, Object input) {
        super.jsonFileWrite(folderName, player.getUniqueId().toString(), input);
    }
}
