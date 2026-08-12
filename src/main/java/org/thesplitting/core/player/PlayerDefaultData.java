package org.thesplitting.core.player;

public record PlayerDefaultData(int defaultLevel, int defaultMaxHealth) {
    public static final PlayerDefaultData DEFAULT = new PlayerDefaultData(1, 4);
}
