package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public abstract class HitEntityGoal extends Goal
{
    public abstract boolean HitCorrectEntityType(EntityType entityType);
    public abstract boolean UsedCorrectItem(Material itemType);
}
