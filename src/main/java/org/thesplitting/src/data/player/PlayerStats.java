package org.thesplitting.src.data.player;

public class PlayerStats {
    private int maxHealth;
    private int level;
    private int inferno;
    private int bolt;
    private int water_flow;
    private int green_flame;

    public PlayerStats(int maxHealth, int level, int inferno, int bolt, int water_flow, int green_flame) {
        this.maxHealth = maxHealth;
        this.level = level;
        this.inferno = inferno;
        this.bolt = bolt;
        this.water_flow = water_flow;
        this.green_flame = green_flame;
    }

    public int getBolt() {
        return bolt;
    }

    public void setBolt(int bolt) {
        this.bolt = bolt;
    }

    public int getGreen_flame() {
        return green_flame;
    }

    public void setGreen_flame(int green_flame) {
        this.green_flame = green_flame;
    }

    public int getInferno() {
        return inferno;
    }

    public void setInferno(int inferno) {
        this.inferno = inferno;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getWater_flow() {
        return water_flow;
    }

    public void setWater_flow(int water_flow) {
        this.water_flow = water_flow;
    }
}
