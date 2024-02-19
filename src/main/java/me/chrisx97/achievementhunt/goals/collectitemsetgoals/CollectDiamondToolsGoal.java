package me.chrisx97.achievementhunt.goals.collectitemsetgoals;

import me.chrisx97.achievementhunt.goals.base.CollectItemSetGoal;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class CollectDiamondToolsGoal extends CollectItemSetGoal
{
    private Player whoClaimedGoal;
    private ItemStack myItem;
    private List<Material> itemSetMaterials;
    private boolean isCompleted = false;

    @Override
    public ItemStack GetDisplayItem()
    {
        if (myItem == null)
        {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.DIAMOND_HOE, GetName(), false, null, GetDescription());
        }
        if (isCompleted) {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.DIAMOND_HOE, GetName(), true, whoClaimedGoal, GetDescription());
        }


        return myItem;
    }

    @Override
    public String GetName()
    {
        return "Collect Diamond Tools";
    }

    @Override
    public List<String> GetDescription()
    {
        List<String> description = new ArrayList<>();
        description.add("&7Awarded for having a full");
        description.add("&7set of &bDiamond Tools&7.");
        return description;
    }

    @Override
    public boolean IsCompleted()
    {
        return isCompleted;
    }

    @Override
    public void SetCompletion(boolean newState)
    {
        isCompleted = newState;
    }

    @Override
    public Player WhoClaimedGoal()
    {
        return whoClaimedGoal;
    }

    @Override
    public void SetWhoClaimedGoal(Player player)
    {
        whoClaimedGoal = player;
    }

    @Override
    public boolean HasCorrectItems(PlayerInventory inventory)
    {
        if (itemSetMaterials == null)
        {
            itemSetMaterials = new ArrayList<>();
            itemSetMaterials.add(Material.DIAMOND_AXE);
            itemSetMaterials.add(Material.DIAMOND_SHOVEL);
            itemSetMaterials.add(Material.DIAMOND_PICKAXE);
            itemSetMaterials.add(Material.DIAMOND_HOE);
        }

        if (inventory.contains(itemSetMaterials.get(0)))
        {
            if (inventory.contains(itemSetMaterials.get(1)))
            {
                if (inventory.contains(itemSetMaterials.get(2)))
                {
                    if (inventory.contains(itemSetMaterials.get(3)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
