package org.thesplitting.src.item.CollectableItems.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thesplitting.src.item.CollectableItems.ItemCategories;
import org.thesplitting.src.item.CollectableItems.ICollectableItem;

import java.util.List;

public class FireSword implements ICollectableItem {
    public static final String ID = "fire_sword";
    public static final String NAME = "Fire Sword";
    public static final String CATEGORY = ItemCategories.WEAPON.toString();

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
        meta.setLore(List.of(CATEGORY));
        fireSword.setItemMeta(meta);

        return fireSword;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }
}
