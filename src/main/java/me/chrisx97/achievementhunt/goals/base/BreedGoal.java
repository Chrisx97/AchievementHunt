package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.entity.EntityType;

public abstract class BreedGoal extends Goal
{
    public abstract boolean CorrectType(EntityType breedType);
}
