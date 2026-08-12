package org.thesplitting.exceptions;

public class PlayerDataNotFoundException extends RuntimeException {
    public PlayerDataNotFoundException(String playerName) {
        super("No player data found for: " + playerName);
    }
}
