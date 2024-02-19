package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public abstract class KillGoal extends Goal
{
    public abstract boolean KilledCorrectType(EntityType entityType);
    public abstract boolean UsedCorrectItem(Material itemType);
}
