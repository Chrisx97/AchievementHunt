package me.chrisx97.achievementhunt.utils;

import me.chrisx97.achievementhunt.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AchievementGUI implements Listener
{
    private static Inventory menu;

    public AchievementGUI()
    {
        menu = Bukkit.createInventory(null, 54, "Achievement Hunters");
        InitializeItems();
    }

    public static void OpenGUI(final HumanEntity ent)
    {
        ent.closeInventory();
        ent.openInventory(menu);
    }

    public static void InitializeItems()
    {
        ItemStack fillerItem = CreateGuiItem(Material.BLACK_STAINED_GLASS_PANE, " ");

        for (int i = 0; i < 9; i++) { //This creates the top row
            menu.setItem(i, fillerItem);
        }

        for (int i = 45; i < 54; i++)
        { //this creates the bottom row
            menu.setItem(i, fillerItem);
        }
        menu.setItem(9, fillerItem);
        menu.setItem(10, fillerItem);
        menu.setItem(16, fillerItem);
        menu.setItem(17, fillerItem);
        menu.setItem(18, fillerItem);
        menu.setItem(19, fillerItem);
        menu.setItem(25, fillerItem);
        menu.setItem(26, fillerItem);
        menu.setItem(27, fillerItem);
        menu.setItem(28, fillerItem);
        menu.setItem(34, fillerItem);
        menu.setItem(35, fillerItem);
        menu.setItem(36, fillerItem);
        menu.setItem(37, fillerItem);
        menu.setItem(43, fillerItem);
        menu.setItem(44, fillerItem);

        if (!GameManager.GetInstance().GetActiveGoalList().isEmpty())
        {
            int length = GameManager.GetInstance().GetActiveGoalList().size();
            int indexOffset = 0;
            for (int i = 0; i < length; i++)
            {
                if (i <= 4)
                {
                    indexOffset = i + 11;
                }

                if (i > 4 && i <= 9)
                {
                    indexOffset = i + 15;
                }

                if (i > 9 && i <= 14)
                {
                    indexOffset = i + 19;
                }

                if (i > 14 && i <= 19)
                {
                    indexOffset = i + 23;
                }

                menu.setItem(indexOffset, GameManager.GetInstance().GetActiveGoalList().get(i).GetDisplayItem());
            }
        }
    }


    protected static ItemStack CreateGuiItem(final Material material, final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // Check for clicks on items
    @EventHandler
    public void OnInventoryClick(final InventoryClickEvent e)
    {
        if (e.getInventory().equals(menu))
        {
            e.setCancelled(true);
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void OnInventoryClick(final InventoryDragEvent e)
    {
        if (e.getInventory().equals(menu))
        {
            e.setCancelled(true);
        }
    }

    /*@EventHandler
    public void OnCloseInventory(InventoryCloseEvent event)
    {
        //fix for weird edge case where dropping items after closing GUI causes it to re-appear?
        if (event.getInventory().equals(menu))
        {
            menu.getViewers().remove(event.getPlayer());
        }
    }*/
}
