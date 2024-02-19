package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.entity.EntityType;

public abstract class BucketFishingGoal extends Goal
{
    public abstract boolean IsCorrectType(EntityType entityType);
}
