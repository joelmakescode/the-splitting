package org.thesplitting.src.data.player;

public record PlayerInventory(String[] inventorySlots) {

    public String getInventorySlot(int slot) {
        return inventorySlots[slot];
    }

    public void setInventorySlot(int slot, String inventorySlot) {
        inventorySlots[slot] = inventorySlot;
    }

}
