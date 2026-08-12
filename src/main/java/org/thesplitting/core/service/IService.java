package org.thesplitting.core.service;

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
