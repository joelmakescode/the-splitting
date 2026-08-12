package org.thesplitting.core.entities;

public interface IEntity {

    /**
     * Returns the name of an entity.
     * @return name of entity
     */
    String getName();

    /**
     * Returns the maximum health of an entity.
     * @return max health of an entity
     */
    int getMaxHealth();

    /**
     * Changes the name of the user.
     * @param name of the user
     */
    void setName(String name);

    /**
     * Changes the maxHealth of a user.
     * @param maxHealth of a user
     */
    void setMaxHealth(int maxHealth);
}
