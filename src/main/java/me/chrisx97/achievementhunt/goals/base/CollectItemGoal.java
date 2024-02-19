package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;

public abstract class CollectItemGoal extends Goal
{
    public abstract boolean HasCorrectItem(Material itemType);
}
