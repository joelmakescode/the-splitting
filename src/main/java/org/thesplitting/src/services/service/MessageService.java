package org.thesplitting.src.services.service;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.thesplitting.src.services.IService;
import org.thesplitting.src.services.ServiceRegistry;

public record MessageService(ServiceRegistry registry) implements IService {
    private static final String TITLE = ChatColor.GOLD + "[The Splitting] ";

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static void successMessage(Player player, String message) {
        player.sendMessage(TITLE + ChatColor.GREEN + message);
    }

    public static void infoMessage(Player player, String message) {
        player.sendMessage(TITLE + ChatColor.BLUE + message);
    }

    public static void warnMessage(Player player, String message) {
        player.sendMessage(TITLE + ChatColor.YELLOW + message);
    }

    public static void errorMessage(Player player, String message) {
        player.sendMessage(TITLE + ChatColor.RED + message);
    }
}
