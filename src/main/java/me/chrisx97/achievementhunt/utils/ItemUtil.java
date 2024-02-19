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

        Material type = item.getType();
        if (IsWeirdItem(type))
        {
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
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

    public boolean IsCompostable(Material mat) {
        switch (mat) {
            case DRIED_KELP:
            case GLOW_BERRIES:
            case SHORT_GRASS:
            case GRASS_BLOCK:
            case HANGING_ROOTS:
            case KELP:
            case MELON_SEEDS:
            case MOSS_CARPET:
            case PUMPKIN_SEEDS:
            case SEAGRASS:
            case SMALL_DRIPLEAF:
            case SWEET_BERRIES:
            case WHEAT_SEEDS:
            case CACTUS:
            case DRIED_KELP_BLOCK:
            case FLOWERING_AZALEA_LEAVES:
            case GLOW_LICHEN:
            case MELON_SLICE:
            case NETHER_SPROUTS:
            case SUGAR_CANE:
            case TALL_GRASS:
            case WEEPING_VINES:
            case TWISTING_VINES:
            case APPLE:
            case AZALEA:
            case BEETROOT:
            case BIG_DRIPLEAF:
            case CARROT:
            case COCOA_BEANS:
            case LILY_PAD:
            case MELON:
            case MOSS_BLOCK:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case MUSHROOM_STEM:
            case NETHER_WART:
            case POTATO:
            case SEA_PICKLE:
            case SHROOMLIGHT:
            case SPORE_BLOSSOM:
            case WHEAT:
            case CRIMSON_FUNGUS:
            case WARPED_FUNGUS:
            case CRIMSON_ROOTS:
            case WARPED_ROOTS:
            case BAKED_POTATO:
            case BREAD:
            case COOKIE:
            case FLOWERING_AZALEA:
            case NETHER_WART_BLOCK:
            case WARPED_WART_BLOCK:
            case CAKE:
            case PUMPKIN_PIE:
            case OAK_LEAVES:
            case SPRUCE_LEAVES:
            case BIRCH_LEAVES:
            case JUNGLE_LEAVES:
            case ACACIA_LEAVES:
            case DARK_OAK_LEAVES:
            case AZALEA_LEAVES:
            case OAK_SAPLING:
            case SPRUCE_SAPLING:
            case BIRCH_SAPLING:
            case JUNGLE_SAPLING:
            case ACACIA_SAPLING:
            case DARK_OAK_SAPLING:
            case VINE:
            case FERN:
            case LARGE_FERN:
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case RED_TULIP:
            case ORANGE_TULIP:
            case WHITE_TULIP:
            case PINK_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case WITHER_ROSE:
            case SUNFLOWER:
            case LILAC:
            case ROSE_BUSH:
            case PEONY:
            case PUMPKIN:
            case CARVED_PUMPKIN:
            case HAY_BLOCK:
            case BROWN_MUSHROOM_BLOCK:
            case RED_MUSHROOM_BLOCK:
                return true;

            default:
                return false;
        }
    }

        public boolean IsWeirdItem(Material mat) {
            switch (mat) {
                case SPAWNER:
                case GOLDEN_HOE:
                case GOLDEN_AXE:
                case GOLDEN_SHOVEL:
                case GOLDEN_PICKAXE:
                case DIAMOND_HOE:
                case DIAMOND_AXE:
                case DIAMOND_SHOVEL:
                case DIAMOND_PICKAXE:
                    return true;

                default:
                    return false;
            }
        }
}
