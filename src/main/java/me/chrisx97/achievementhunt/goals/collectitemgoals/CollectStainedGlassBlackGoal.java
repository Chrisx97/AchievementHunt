package me.chrisx97.achievementhunt.goals.collectitemgoals;

import me.chrisx97.achievementhunt.goals.base.CollectItemGoal;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CollectStainedGlassBlackGoal extends CollectItemGoal
{
    private Player whoClaimedGoal;
    private ItemStack myItem;
    private boolean isCompleted = false;

    @Override
    public ItemStack GetDisplayItem()
    {
        if (myItem == null)
        {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.BLACK_STAINED_GLASS, GetName(), false, null, GetDescription());
        }
        if (isCompleted) {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.BLACK_STAINED_GLASS, GetName(), true, whoClaimedGoal, GetDescription());
        }


        return myItem;
    }

    @Override
    public String GetName()
    {
        return "Obtain Black Stained Glass";
    }

    @Override
    public List<String> GetDescription()
    {
        List<String> description = new ArrayList<>();
        description.add("&7Awarded for obtaining");
        description.add("&fBlack Stained Glass&7.");
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
        if (itemType == Material.BLACK_STAINED_GLASS)
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
