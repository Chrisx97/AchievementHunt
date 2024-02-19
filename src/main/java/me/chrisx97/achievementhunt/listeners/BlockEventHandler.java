package me.chrisx97.achievementhunt.listeners;

import me.chrisx97.achievementhunt.goals.base.Goal;
import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.game.GameState;
import me.chrisx97.achievementhunt.goals.base.BlockCollectGoal;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BlockEventHandler implements Listener
{
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        if (GameManager.GetInstance().GetState() == GameState.ACTIVE)
        {
            List<Goal> goalList = GameManager.GetInstance().GetActiveGoalList();

            for (Goal goal : goalList)
            {
                if (goal instanceof BlockCollectGoal)
                {
                    BlockCollectGoal blockGoal = (BlockCollectGoal) goal;

                    if (blockGoal.HarvestedCorrectBlock(blockType))
                    {
                        GameManager.GetInstance().TryClaimGoal(player, blockGoal);
                        break;
                    }
                }
            }
        }
    }
}
