package org.thesplitting.core.errorhandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.thesplitting.core.service.IService;
import org.thesplitting.core.service.ServiceRegistry;

public record PlayerErrorHandler(ServiceRegistry registry) implements IService {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void playerDataNotFoundError(Player player) {
        player.kickPlayer(ChatColor.RED + "Your Player Data could not be loaded. Please try again.");
        registry.getPlugin().getLogger().warning("Player Data could not be loaded for: " + player.getName());
    }
}
