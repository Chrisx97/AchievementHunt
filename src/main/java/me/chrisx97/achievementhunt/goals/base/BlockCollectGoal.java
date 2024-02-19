package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.Material;

public abstract class BlockCollectGoal extends Goal
{
    public abstract boolean HarvestedCorrectBlock(Material type);
}
