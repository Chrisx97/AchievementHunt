package me.chrisx97.achievementhunt.goals.potioneffectgoals;

import me.chrisx97.achievementhunt.goals.base.PotionEffectGoal;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class AbsorptionEffectGoal extends PotionEffectGoal
{
    private Player whoClaimedGoal;
    private ItemStack myItem;
    private boolean isCompleted = false;

    @Override
    public ItemStack GetDisplayItem()
    {
        if (myItem == null)
        {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.GOLDEN_APPLE, GetName(), false, null, GetDescription());
        }
        if (isCompleted) {
            myItem = ItemUtil.Instance.CreateGuiItem(Material.GOLDEN_APPLE, GetName(), true, whoClaimedGoal, GetDescription());
        }
        return myItem;
    }

    @Override
    public String GetName()
    {
        return "Absorption Effect";
    }

    @Override
    public List<String> GetDescription()
    {
        List<String> description = new ArrayList<>();
        description.add("&7Awarded for having the");
        description.add("&fAbsorption &7potion effect active.");
        return description;
    }

    @Override
    public boolean IsCompleted()
    {
        return isCompleted;
    }

    @Override
    public void SetCompletion(boolean newState)
    {
        isCompleted = newState;
    }

    @Override
    public Player WhoClaimedGoal()
    {
        return whoClaimedGoal;
    }

    @Override
    public void SetWhoClaimedGoal(Player player)
    {
        whoClaimedGoal = player;
    }

    @Override
    public boolean CorrectPotionEffect(PotionEffectType effectType)
    {
        if (effectType == PotionEffectType.ABSORPTION)
        {
            return true;
        }

        return false;
    }
}
