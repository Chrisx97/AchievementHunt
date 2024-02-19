package me.chrisx97.achievementhunt.listeners;

import me.chrisx97.achievementhunt.goals.base.Goal;
import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.goals.base.BreedGoal;
import me.chrisx97.achievementhunt.goals.base.EntityInteractGoal;
import me.chrisx97.achievementhunt.goals.base.HitEntityGoal;
import me.chrisx97.achievementhunt.goals.base.KillGoal;
import me.chrisx97.achievementhunt.utils.AchievementGUI;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class EntityEventHandler implements Listener
{

    @EventHandler
    public void OnInteract(PlayerInteractEvent event)
    {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (ItemUtil.Instance.UsedTrackingCompass(event.getPlayer().getInventory().getItemInMainHand()))
        {
            AchievementGUI.OpenGUI(event.getPlayer());
        }
    }

    //Interact Event
    @EventHandler
    public void OnEntityInteract(PlayerInteractEntityEvent event)
    {
        //Only checking main hand
        if (event.getHand() != EquipmentSlot.HAND) return;

        //Loop through the goal list
        List<Goal> goalList = GameManager.GetInstance().GetActiveGoalList();
        for (Goal goal : goalList)
        {
            //check for valid Entity Interact Goal
            if (goal instanceof EntityInteractGoal)
            {
                EntityInteractGoal interactGoal = (EntityInteractGoal) goal;
                //check for valid entity clicked
                if (interactGoal.InteractionEntity() == event.getRightClicked().getType())
                    {
                        //check for correct held item
                        if (interactGoal.UsedCorrectItem(event.getPlayer().getInventory().getItemInMainHand().getType()))
                    {
                        //try to claim the goal
                        GameManager.GetInstance().TryClaimGoal(event.getPlayer(), goal);
                    }
                }
            }
        }
    }



    //Death Event
    @EventHandler
    public void OnEntityDeath(EntityDeathEvent event)
    {
        if (event.getEntity().getKiller() != null)
        {
            Player player = event.getEntity().getKiller();
            LivingEntity killed = event.getEntity();
            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
            {
                if (goal instanceof KillGoal)
                {
                    KillGoal killGoal = (KillGoal) goal;
                    if (killGoal.KilledCorrectType(killed.getType()))
                    {
                        if (killGoal.UsedCorrectItem(player.getInventory().getItemInMainHand().getType()))
                        {
                            GameManager.GetInstance().TryClaimGoal(player, goal);
                        }
                    }
                }
            }

        }
    }

    //Entity Hit Event
    @EventHandler
    public void OnHitEntity(EntityDamageByEntityEvent event)
    {
        Entity attacker = event.getDamager();
        Entity target = event.getEntity();

        if (attacker instanceof Player)
        {
            Player player = (Player) attacker;

            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
            {
                if (goal instanceof HitEntityGoal)
                {
                    HitEntityGoal hitEntityGoal = (HitEntityGoal) goal;
                    if (hitEntityGoal.HitCorrectEntityType(target.getType()))
                    {
                        if (hitEntityGoal.UsedCorrectItem(player.getInventory().getItemInMainHand().getType()))
                        {
                            GameManager.GetInstance().TryClaimGoal(player, goal);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnBreedEvent(EntityBreedEvent event)
    {
        if (event.getBreeder() instanceof Player)
        {
            Player player = (Player) event.getBreeder();
            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
            {
                if (goal instanceof BreedGoal)
                {
                    BreedGoal breedGoal = (BreedGoal) goal;
                    if (breedGoal.CorrectType(event.getFather().getType()))
                    {
                        GameManager.GetInstance().TryClaimGoal(player, goal);
                        break;
                    }
                }
            }
        }
    }
}