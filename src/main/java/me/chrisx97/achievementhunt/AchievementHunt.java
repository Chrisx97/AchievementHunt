package me.chrisx97.achievementhunt;

import me.chrisx97.achievementhunt.commands.AchievementHuntCommand;
import me.chrisx97.achievementhunt.commands.GoalsCommand;
import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.goals.*;
import me.chrisx97.achievementhunt.goals.blockbreakgoals.*;
import me.chrisx97.achievementhunt.goals.breedgoals.BreedChickensGoal;
import me.chrisx97.achievementhunt.goals.breedgoals.BreedCowsGoal;
import me.chrisx97.achievementhunt.goals.bucketfishinggoals.CatchAxolotlGoal;
import me.chrisx97.achievementhunt.goals.bucketfishinggoals.CatchCodGoal;
import me.chrisx97.achievementhunt.goals.bucketfishinggoals.CatchSalmonGoal;
import me.chrisx97.achievementhunt.goals.bucketfishinggoals.CatchTropicalFishGoal;
import me.chrisx97.achievementhunt.goals.collectitemgoals.CollectCakeGoal;
import me.chrisx97.achievementhunt.goals.collectitemgoals.CollectEnchantedAppleGoal;
import me.chrisx97.achievementhunt.goals.collectitemgoals.CollectHeartOfTheSeaGoal;
import me.chrisx97.achievementhunt.goals.collectitemsetgoals.CollectDiamondToolsGoal;
import me.chrisx97.achievementhunt.goals.collectitemsetgoals.CollectGoldToolsGoal;
import me.chrisx97.achievementhunt.goals.eatgoals.EatGlowberryGoal;
import me.chrisx97.achievementhunt.goals.eatgoals.EatRabbitStewGoal;
import me.chrisx97.achievementhunt.goals.interactiongoals.HitGolemWithRoseGoal;
import me.chrisx97.achievementhunt.goals.interactiongoals.MilkCowGoal;
import me.chrisx97.achievementhunt.goals.interactiongoals.UseComposterGoal;
import me.chrisx97.achievementhunt.goals.killgoals.KillSkeletonWithBoneGoal;
import me.chrisx97.achievementhunt.goals.killgoals.KillSnowGolemGoal;
import me.chrisx97.achievementhunt.goals.killgoals.KillWitchGoal;
import me.chrisx97.achievementhunt.goals.killgoals.KillZombieVillagerGoal;
import me.chrisx97.achievementhunt.listeners.BlockEventHandler;
import me.chrisx97.achievementhunt.listeners.EntityEventHandler;
import me.chrisx97.achievementhunt.listeners.FishingHandler;
import me.chrisx97.achievementhunt.listeners.PlayerEventHandler;
import me.chrisx97.achievementhunt.utils.AchievementGUI;
import me.chrisx97.achievementhunt.utils.LoggerUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class AchievementHunt extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        LoggerUtil.Instance().Initialize(this);
        GameManager.GetInstance().Initialize(this);
        InitializeGoals();

        RegisterCommands();
        RegisterListeners();
    }

    private void InitializeGoals()
    {
        GameManager.GetInstance().AddGoal(new MineIronOreGoal());
        GameManager.GetInstance().AddGoal(new MineObsidianGoal());
        GameManager.GetInstance().AddGoal(new MineDiamondOreGoal());
        GameManager.GetInstance().AddGoal(new MineMobSpawnerGoal());
        GameManager.GetInstance().AddGoal(new HarvestSugarCaneGoal());

        GameManager.GetInstance().AddGoal(new BreedChickensGoal());
        GameManager.GetInstance().AddGoal(new BreedCowsGoal());

        GameManager.GetInstance().AddGoal(new CatchAxolotlGoal());
        GameManager.GetInstance().AddGoal(new CatchTropicalFishGoal());
        GameManager.GetInstance().AddGoal(new CatchCodGoal());
        GameManager.GetInstance().AddGoal(new CatchSalmonGoal());

        GameManager.GetInstance().AddGoal(new CollectGoldToolsGoal());
        GameManager.GetInstance().AddGoal(new CollectDiamondToolsGoal());

        GameManager.GetInstance().AddGoal(new CollectCakeGoal());
        GameManager.GetInstance().AddGoal(new CollectEnchantedAppleGoal());
        GameManager.GetInstance().AddGoal(new CollectHeartOfTheSeaGoal());

        GameManager.GetInstance().AddGoal(new EatGlowberryGoal());
        GameManager.GetInstance().AddGoal(new EatRabbitStewGoal());

        GameManager.GetInstance().AddGoal(new HitGolemWithRoseGoal());
        GameManager.GetInstance().AddGoal(new MilkCowGoal());
        GameManager.GetInstance().AddGoal(new UseComposterGoal());

        GameManager.GetInstance().AddGoal(new KillSkeletonWithBoneGoal());
        GameManager.GetInstance().AddGoal(new KillSnowGolemGoal());
        GameManager.GetInstance().AddGoal(new KillZombieVillagerGoal());
        GameManager.GetInstance().AddGoal(new KillWitchGoal());

        GameManager.GetInstance().AddGoal(new DieToCactusGoal());
        GameManager.GetInstance().AddGoal(new EmptyHungerBarGoal());
        GameManager.GetInstance().AddGoal(new FishingGoal());
        GameManager.GetInstance().AddGoal(new WriteBookGoal());
    }

    private void RegisterListeners()
    {
        getServer().getPluginManager().registerEvents(new BlockEventHandler(), this);
        getServer().getPluginManager().registerEvents(new EntityEventHandler(), this);
        getServer().getPluginManager().registerEvents(new FishingHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(), this);
        getServer().getPluginManager().registerEvents(new AchievementGUI(), this);
    }
    private void RegisterCommands()
    {
        this.getCommand("Goals").setExecutor(new GoalsCommand());
        this.getCommand("AchievementHunt").setExecutor(new AchievementHuntCommand());
    }
}