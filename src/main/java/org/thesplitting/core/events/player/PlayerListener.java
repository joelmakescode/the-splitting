package org.thesplitting.core.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.thesplitting.core.player.PlayerService;
import org.thesplitting.core.service.IService;
import org.thesplitting.core.service.ServiceRegistry;

public class PlayerListener implements Listener, IService {
    private final ServiceRegistry registry;
    private final PlayerService playerService;

    public PlayerListener(ServiceRegistry registry, PlayerService playerService) {
        this.registry = registry;
        this.playerService = playerService;
    }

    @Override
    public void onEnable() {
        registry.getPlugin().getServer().getPluginManager().registerEvents(this, registry.getPlugin());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        playerService.loadPlayerFile(player);
        playerService.initiatePlayerSetup(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        playerService.savePlayerFile(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        // Start filling inventory again
    }
}
