package org.thesplitting.src.data.item.PlayerItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thesplitting.src.data.item.CollectableItems.ItemCategories;
import org.thesplitting.src.data.contracts.ICollectableItem;
import org.thesplitting.src.data.item.CollectableItems.ItemInventorySlots;

import java.util.UUID;

public class StartSwordItem implements ICollectableItem {
    public static final String ID = "start_sword";
    public static final String NAME = "Starter Sword";
    public static final String DISPLAY_NAME = ChatColor.DARK_GRAY + NAME;
    public static final UUID ATTRIBUTE_MODIFIER_UUID = UUID.randomUUID();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta meta = itemStack.getItemMeta();
        if  (meta != null) {
            meta.setDisplayName(DISPLAY_NAME);
            meta.addAttributeModifier(
                Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(
                    ATTRIBUTE_MODIFIER_UUID,
                    "custom_damage",
                    2.0,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlot.HAND
                )
            );
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getDefaultValue() {
        return 1;
    }

    @Override
    public ItemCategories getItemCategory() {
        return ItemCategories.MELEE;
    }

    @Override
    public ItemInventorySlots getInventorySlot() {
        return ItemInventorySlots.MELEE;
    }
}
