package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public abstract class CollectItemSetGoal extends Goal
{
    //public abstract List<Material> GetSetTypes();
    public abstract boolean HasCorrectItems(PlayerInventory inventory);
}
