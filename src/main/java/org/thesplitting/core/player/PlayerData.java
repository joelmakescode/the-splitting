package org.thesplitting.core.player;

import org.thesplitting.core.entities.IEntity;

public class PlayerData implements IEntity {
    private String name;
    private int maxHealth;
    private int level;

    public PlayerData(String name, int maxHealth, int level) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.level = level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Player Inventory to come
}
