package me.chrisx97.achievementhunt.goals.collectitemgoals;

import me.chrisx97.achievementhunt.goals.base.CollectItemGoal;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CollectSlimeBlockGoal extends CollectItemGoal
{
    private Player whoClaimedGoal;
    private ItemStack myItem;
    private boolean isCompleted = false;

    @Override
    public ItemStack GetDisplayItem()
    {
        if (myItem == null)
        {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.SLIME_BLOCK, GetName(), false, null, GetDescription());
        }
        if (isCompleted) {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.SLIME_BLOCK, GetName(), true, whoClaimedGoal, GetDescription());
        }


        return myItem;
    }

    @Override
    public String GetName()
    {
        return "Slime Rancher";
    }


    @Override
    public List<String> GetDescription()
    {
        List<String> description = new ArrayList<>();
        description.add("&7Awarded for collecting");
        description.add("&aSlime Block&7.");
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
    public boolean HasCorrectItem(Material itemType)
    {
        if (itemType == Material.SLIME_BLOCK)
        {
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
