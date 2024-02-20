package me.chrisx97.achievementhunt.listeners;

import me.chrisx97.achievementhunt.goals.OpponentTakesFallDamageGoal;
import me.chrisx97.achievementhunt.goals.base.BucketFishingGoal;
import me.chrisx97.achievementhunt.goals.base.Goal;
import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.goals.DieToCactusGoal;
import me.chrisx97.achievementhunt.goals.collectitemgoals.CollectCakeGoal;
import me.chrisx97.achievementhunt.goals.EmptyHungerBarGoal;
import me.chrisx97.achievementhunt.goals.WriteBookGoal;
import me.chrisx97.achievementhunt.goals.base.EatGoal;
import me.chrisx97.achievementhunt.goals.interactiongoals.UseComposterGoal;
import me.chrisx97.achievementhunt.utils.AchievementGUI;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;

public class PlayerEventHandler implements Listener
{
    private HashMap<Player, Long> playerDropCooldowns = new HashMap<>();
    @EventHandler
    public void OnDropItem(PlayerDropItemEvent event)
    {
        playerDropCooldowns.put(event.getPlayer(), System.currentTimeMillis() + 15);
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event)
    {
        if (event.getHand() != EquipmentSlot.HAND) return;
        boolean onCooldown;
        if (playerDropCooldowns.containsKey(event.getPlayer()) && System.currentTimeMillis() < playerDropCooldowns.get(event.getPlayer()))
        {
            onCooldown = true;
        } else
        {
            onCooldown = false;
        }

        if (ItemUtil.Instance.UsedTrackingCompass(event.getPlayer().getInventory().getItemInMainHand()) && !onCooldown)
        {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                AchievementGUI.OpenGUI(event.getPlayer());
                return;
            }
        }

        if (event.getClickedBlock() != null)
        {
            if (event.getClickedBlock().getType() == Material.COMPOSTER)
            {
                if (ItemUtil.Instance.IsCompostable(event.getPlayer().getInventory().getItemInMainHand().getType()))
                {
                    for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
                    {
                        if (goal instanceof UseComposterGoal)
                        {
                            GameManager.GetInstance().TryClaimGoal(event.getPlayer(), goal);
                            break;
                        }
                    }
                }
            }
        }
    }

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
    public void OnPlayerDamaged(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if (player.getLastDamageCause()!= null) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL)
                {
                    if (GameManager.GetInstance().GetPlayersInGame() > 1)
                    {
                        for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
                        {
                            if (goal instanceof OpponentTakesFallDamageGoal)
                            {
                                GameManager.GetInstance().TryClaimGoal(player, goal);
                            }
                        }
                    }
                }
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

    @EventHandler
    public void PlayerBucketFishing(PlayerBucketEntityEvent event)
    {
        for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
        {
            if (goal instanceof BucketFishingGoal)
            {
                BucketFishingGoal bucketFishingGoal = (BucketFishingGoal) goal;
                if (bucketFishingGoal.IsCorrectType(event.getEntity().getType()))
                {
                    GameManager.GetInstance().TryClaimGoal(event.getPlayer(), goal);
                    break;
                }
            }
        }
    }
}

