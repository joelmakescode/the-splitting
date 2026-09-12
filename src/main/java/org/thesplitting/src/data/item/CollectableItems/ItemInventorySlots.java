package org.thesplitting.src.data.item.CollectableItems;

import org.thesplitting.src.misc.annotations.Nullable;

public enum ItemInventorySlots {
    MELEE(0, "Melee"),
    BOW(1, "Bow"),
    POTION1(2, "Potion 1"),
    POTION2(3, "Potion 2"),
    NONE(-1, "Nothing");

    private final int inventorySlot;
    private final String label;

    ItemInventorySlots(int inventorySlot, String label) {
        this.inventorySlot = inventorySlot;
        this.label = label;
    }

    /**
     * Slot Placeholder
     * @return slot
     */
    public int getInventorySlot() {
        return inventorySlot;
    }

    /**
     * Human-readable name used for slot placeholders.
     * @return Label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Resolves the restriction bound to a hotbar slot.
     * @param slot hotbar slot
     * @return restriction, null if the slot has none
     */
    @Nullable
    public static ItemInventorySlots fromSlot(int slot) {
        if (slot < 0) {
            return null;
        }

        for (ItemInventorySlots i : values()) {
            if (i.inventorySlot == slot) {
                return i;
            }
        }

        return null;
    }
}
