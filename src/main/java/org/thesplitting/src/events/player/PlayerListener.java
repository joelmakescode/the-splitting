package org.thesplitting.src.events.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.thesplitting.src.services.service.PlayerService;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.services.ServiceRegistry;

public record PlayerListener(ServiceRegistry registry, PlayerService playerService) implements Listener, IService {

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
        playerService.initiatePlayerSetup(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        playerService.savePlayerFile(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.setDroppedExp(0);
        e.getDrops().clear();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        Bukkit.getScheduler().runTask(registry.getPlugin(), () -> {
            playerService.initiatePlayerSetup(player);
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        // This has to be made area specific later
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        // This has to be made area specific later
        e.setCancelled(true);
    }
}
