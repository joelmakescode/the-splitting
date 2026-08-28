package org.thesplitting.src.services;

public interface IService {
    /**
     * Wird beim Plugin-Start aufgerufen.
     */
    void onEnable();

    /**
     * Wird beim Plugin-Stop aufgerufen.
     */
    void onDisable();
}
