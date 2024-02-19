package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.inventory.PlayerInventory;

public abstract class CollectItemSetGoal extends Goal
{
    //public abstract List<Material> GetSetTypes();
    public abstract boolean HasCorrectItems(PlayerInventory inventory);
}
