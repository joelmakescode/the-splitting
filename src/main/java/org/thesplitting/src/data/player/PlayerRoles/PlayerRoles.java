package org.thesplitting.src.data.player.PlayerRoles;

import org.bukkit.ChatColor;

public enum PlayerRoles {
    NOVICE(ChatColor.GREEN),
    PRODIGY(ChatColor.DARK_BLUE),
    INTELLECTUAL(ChatColor.GOLD);

    private final ChatColor color;

    PlayerRoles(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
