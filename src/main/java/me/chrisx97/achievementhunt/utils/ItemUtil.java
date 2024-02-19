package me.chrisx97.achievementhunt.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil
{
    public static ItemUtil Instance = new ItemUtil();
    private ItemStack trackingCompass;

    public ItemStack CreateGuiItem(final Material material, final String name, boolean enchanted, Player claimedGoal, List<String> lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(HexFormat.format("&f" + name));

        // Set the lore of the item
        List<String> newLore = new ArrayList<>();
        for (String string : lore)
        {
            newLore.add(HexFormat.format(string));
        }
        if (claimedGoal != null)
        {
            newLore.add("");
            newLore.add(HexFormat.format("&7Claimed by &a" + claimedGoal.getName()));
        }

        if (item.getType() == Material.SPAWNER)
        {
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        }

        if (enchanted)
        {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.LURE, 1, true);
        }

        meta.setLore(newLore);

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack GetTrackingCompass()
    {
        if (trackingCompass == null)
        {
            trackingCompass = new ItemStack(Material.COMPASS, 1);
            ItemMeta meta = trackingCompass.getItemMeta();
            meta.setDisplayName(HexFormat.format("&cPlayer Tracker"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(HexFormat.format("&6Right Click &7to open"));
            lore.add(HexFormat.format("&7the goals GUI."));
            meta.setLore(lore);
            meta.addEnchant(Enchantment.LURE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            trackingCompass.setItemMeta(meta);
        }

        return trackingCompass;
    }

    public boolean UsedTrackingCompass(ItemStack heldItem)
    {
        if (heldItem.getType() == Material.COMPASS)
        {
            ItemMeta playerItemMeta = heldItem.getItemMeta();
            ItemMeta compassMeta = ItemUtil.Instance.GetTrackingCompass().getItemMeta();

            if (playerItemMeta != null)
            {
                String playerItemName = playerItemMeta.getDisplayName();
                String compassDisplayName = "";
                if (compassMeta != null)
                {
                    compassDisplayName = compassMeta.getDisplayName();
                }

                if (playerItemName.equals(compassDisplayName))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
