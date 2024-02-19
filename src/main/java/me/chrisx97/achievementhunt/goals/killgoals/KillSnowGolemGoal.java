package me.chrisx97.achievementhunt.goals.killgoals;

import me.chrisx97.achievementhunt.goals.base.KillGoal;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KillSnowGolemGoal extends KillGoal
{
    private Player whoClaimedGoal;
    private ItemStack myItem;
    private boolean isCompleted = false;

    @Override
    public ItemStack GetDisplayItem()
    {
        if (myItem == null)
        {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.CARVED_PUMPKIN, GetName(), false, null, GetDescription());
        }
        if (isCompleted) {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.CARVED_PUMPKIN, GetName(), true, whoClaimedGoal, GetDescription());
        }
        return myItem;
    }

    @Override
    public String GetName()
    {
        return "Get Frosty";
    }

    @Override
    public List<String> GetDescription()
    {
        List<String> description = new ArrayList<>();
        description.add("&7Awarded for killing a &fSnow Golem&7.");
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
    public boolean KilledCorrectType(EntityType entityType)
    {
        if (entityType == EntityType.SNOWMAN)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean UsedCorrectItem(Material itemType)
    {
        return true;
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
