package org.thesplitting.src.data.player.PlayerRoles;

import org.thesplitting.src.misc.helper.ToolBox;

public class PlayerRole {
    private String playerRole;

    public PlayerRole(String playerRole) {
        this.playerRole = ToolBox.formatEnumToWord(playerRole);
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = ToolBox.formatEnumToWord(playerRole);
    }
}
