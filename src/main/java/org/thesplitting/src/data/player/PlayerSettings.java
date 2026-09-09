package org.thesplitting.src.data.player;

public class PlayerSettings {
    private int isStarter;

    public PlayerSettings(int isStarter) {
        this.isStarter = isStarter;
    }

    public int getIsStarter() {
        return isStarter;
    }

    public void setIsStarter(int isStarter) {
        this.isStarter = isStarter;
    }
}
