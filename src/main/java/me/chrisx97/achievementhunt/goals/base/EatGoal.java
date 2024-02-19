package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;

public abstract class EatGoal extends Goal
{
    public abstract boolean ConsumedCorrectItem(Material consumedItemType);
}
