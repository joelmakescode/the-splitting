package org.thesplitting.src.data.item.CollectableItems.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thesplitting.src.data.item.CollectableItems.ItemCategories;
import org.thesplitting.src.data.contracts.ICollectableItem;

import java.util.List;

public class FireSword implements ICollectableItem {
    public static final String ID = "fire_sword";
    public static final String NAME = "Fire Sword";
    public static final String CATEGORY = ItemCategories.WEAPON.toString();
    public static final String WEAPON_CATEGORY = ItemCategories.MELEE.toString();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack fireSword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta meta = fireSword.getItemMeta();

        meta.setDisplayName(NAME);
        meta.setLore(List.of(CATEGORY, WEAPON_CATEGORY));
        fireSword.setItemMeta(meta);

        return fireSword;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }
}
