package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Goal
{
    public abstract ItemStack GetDisplayItem();
    public abstract String GetName();
    public abstract List<String> GetDescription();
    public abstract Player WhoClaimedGoal();
    public abstract void SetWhoClaimedGoal(Player player);

    public abstract boolean IsCompleted();
    public abstract void SetCompletion(boolean newState);


}
