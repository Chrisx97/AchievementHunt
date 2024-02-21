package me.chrisx97.achievementhunt.listeners;

import me.chrisx97.achievementhunt.game.GameState;
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
import me.chrisx97.achievementhunt.utils.LoggerUtil;
import org.bukkit.Material;
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
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
        playerDropCooldowns.put(event.getPlayer(), System.currentTimeMillis() + 15);
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event)
    {
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
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
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;

        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            LoggerUtil.Instance().Broadcast("&cFoodLevelChangeEvent: &fPlayer &a" + player.getName() + " &ffood level: &a" + player.getFoodLevel());
            if (player.getFoodLevel() <= 0)
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
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
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
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
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
        //early return if the game isn't running, stops other code from executing
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;

        //Make sure the entity involved in the event was a Player
        if (event.getEntity() instanceof Player)
        {
            //cache a reference to it by casting the event Entity to Player type
            Player player = (Player) event.getEntity();

            //make sure the player actually HAS a previous damage cause
            if (player.getLastDamageCause() != null)
            {
                //make sure the last damage type was "FALL DAMAGE"
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL)
                {
                    //make sure there are at least 2 players in the game
                    if (GameManager.GetInstance().PlayersInGameCount() > 1)
                    {
                        //loop through all active goals
                        for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
                        {
                            //if we find the "Opponent Takes Fall Damage" goal
                            if (goal instanceof OpponentTakesFallDamageGoal)
                            {
                                //loop through active players in this game
                                for (Player activePlayer : GameManager.GetInstance().GetPlayersInGame())
                                {
                                    //if we find a player that IS NOT the player who took fall damage
                                    if (activePlayer != player)
                                    {
                                        //try to claim the goal for them
                                        GameManager.GetInstance().TryClaimGoal(activePlayer, goal);
                                    }
                                }
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
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
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
        if (GameManager.GetInstance().GetState() != GameState.ACTIVE) return;
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

