package me.chrisx97.achievementhunt.listeners;

import me.chrisx97.achievementhunt.goals.base.Goal;
import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.goals.DieToCactusGoal;
import me.chrisx97.achievementhunt.goals.collectitemgoals.CollectCakeGoal;
import me.chrisx97.achievementhunt.goals.EmptyHungerBarGoal;
import me.chrisx97.achievementhunt.goals.WriteBookGoal;
import me.chrisx97.achievementhunt.goals.base.EatGoal;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerEventHandler implements Listener
{

    @EventHandler
    public void OnHungerChange(FoodLevelChangeEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if (player.getFoodLevel() == 0)
            {
                for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
                {
                    if (goal instanceof EmptyHungerBarGoal)
                    {
                        GameManager.GetInstance().TryClaimGoal(player, goal);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnPlayerCrafted(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getResult().getType() == Material.CAKE)
        {
            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
            {
                if (goal instanceof CollectCakeGoal)
                {
                    GameManager.GetInstance().TryClaimGoal(player, goal);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void OnPlayerEat(PlayerItemConsumeEvent event)
    {
        Material itemType = event.getItem().getType();
        for (Goal goal : GameManager.GetInstance().GetActiveGoalList()) {
            if (goal instanceof EatGoal)
            {
                EatGoal eatGoal = (EatGoal) goal;
                if (eatGoal.ConsumedCorrectItem(itemType))
                {
                    GameManager.GetInstance().TryClaimGoal(event.getPlayer(), goal);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void OnWriteBook(PlayerEditBookEvent event)
    {
        for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
        {
            if (goal instanceof WriteBookGoal)
            {
                GameManager.GetInstance().TryClaimGoal(event.getPlayer(), goal);
                break;
            }
        }
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();

        if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CONTACT)
        {
            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
            {
                if (goal instanceof DieToCactusGoal)
                {
                    GameManager.GetInstance().TryClaimGoal(player, goal);
                    break;
                }
            }
        }
    }
}

