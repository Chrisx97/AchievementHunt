package me.chrisx97.achievementhunt.listeners;

import me.chrisx97.achievementhunt.game.GameState;
import me.chrisx97.achievementhunt.goals.base.Goal;
import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.goals.FishingGoal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingHandler implements Listener
{
    @EventHandler
    public void onCatch(PlayerFishEvent e){
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
        Entity entity = e.getCaught();
        if(entity instanceof Item){
            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
            {
                if (goal instanceof FishingGoal)
                {
                    GameManager.GetInstance().TryClaimGoal(e.getPlayer(), goal);
                    break;
                }
            }
        }
    }
}
