package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public abstract class EntityInteractGoal extends Goal
{
    public abstract EntityType InteractionEntity();
    public abstract boolean UsedCorrectItem(Material itemType);
}
