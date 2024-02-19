package me.chrisx97.achievementhunt.goals.blockbreakgoals;

import me.chrisx97.achievementhunt.goals.base.BlockCollectGoal;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HarvestSugarCaneGoal extends BlockCollectGoal
{
    private Player whoClaimedGoal;
    private ItemStack myItem;
    private boolean isCompleted = false;

    @Override
    public ItemStack GetDisplayItem()
    {
        if (myItem == null)
        {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.SUGAR_CANE, GetName(), false, null, GetDescription());
        }
        if (isCompleted) {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.SUGAR_CANE, GetName(), true, whoClaimedGoal, GetDescription());
        }
        return myItem;
    }

    @Override
    public String GetName()
    {
        return "Harvest Sugarcane";
    }

    @Override
    public List<String> GetDescription()
    {
        List<String> description = new ArrayList<>();
        description.add("&7Awarded to the first");
        description.add("&7player to harvest &aSugar Cane&7.");
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
    public boolean HarvestedCorrectBlock(Material type)
    {
        if (type == Material.SUGAR_CANE) {
            return true;
        }
        return false;
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
}
