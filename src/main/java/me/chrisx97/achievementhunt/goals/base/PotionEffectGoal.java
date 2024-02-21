package me.chrisx97.achievementhunt.goals.base;

import org.bukkit.potion.PotionEffectType;

public abstract class PotionEffectGoal extends Goal
{
    public abstract boolean CorrectPotionEffect(PotionEffectType effectType);
}
