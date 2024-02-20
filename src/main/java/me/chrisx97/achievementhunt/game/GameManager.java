package me.chrisx97.achievementhunt.game;

import me.chrisx97.achievementhunt.goals.base.CollectItemSetGoal;
import me.chrisx97.achievementhunt.goals.base.Goal;
import me.chrisx97.achievementhunt.goals.base.CollectItemGoal;
import me.chrisx97.achievementhunt.utils.AchievementGUI;
import me.chrisx97.achievementhunt.utils.HexFormat;
import me.chrisx97.achievementhunt.utils.ItemUtil;
import me.chrisx97.achievementhunt.utils.LoggerUtil;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Logger;

public class GameManager
{
    private static GameManager instance = new GameManager();
    private Plugin plugin;
    private GameState currentState = GameState.WAITING;

    private Player winningPlayer = null;
    private List<Goal> goalList = new ArrayList<>();
    private List<Goal> activeGoalList = new ArrayList<>();
    private final Dictionary<Player, List<Goal>> playerData = new Hashtable<>();


    public void AddGoal(Goal goal)
    {
        goalList.add(goal);
    }

    public GameState GetState()
    {
        return currentState;
    }


    public void Initialize(Plugin plugin) {
        this.plugin = plugin;
    }
    public static GameManager GetInstance()
    {
        return instance;
    }

    public void SetState(GameState newState) {
        if (currentState == newState) return;

        switch (newState) {
            case WAITING:
                currentState = newState;
                break;
            case ACTIVE:
                currentState = newState;
                StartGame();
                break;
            case ENDING:
                currentState = newState;
                DoThunder();
                SwitchPlayersToSpectator();
                StartGameEndingTask();
                break;
        }
    }

    private void StartGame()
    {
        activeGoalList = pickNRandom(GetGoalList(), 20);
        TeleportPlayersToSpawn();
        ResetPlayers(true);
        DoThunder();
        StartMonitorInventoryTask();
        StartCompassTrackerTask();
        LoggerUtil.Instance().Broadcast("&6Match is starting!");
        LoggerUtil.Instance().Broadcast("&cFirst to complete &a10 goals &cwins!");
        AchievementGUI.InitializeItems();
        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            playerData.put(player, new ArrayList<>());
            AchievementGUI.OpenGUI(player);
        }
    }


    public List<Goal> GetGoalList()
    {
        return goalList;
    }
    public List<Goal> GetActiveGoalList() {

        return activeGoalList;
    }

    public Dictionary<Player, List<Goal>> GetPlayerData()
    {
        return playerData;
    }

    public boolean TryClaimGoal(Player player, Goal goal)
    {
        if (GetActiveGoalList().contains(goal))
        {
            List<Goal> playerGoals = playerData.get(player);
            Enumeration<Player> playerDataEnumerator = playerData.keys();

            for (int i = 0; i < 10; i++)
            {
                if (playerDataEnumerator.hasMoreElements())
                {
                    Player next = playerDataEnumerator.nextElement();
                    if (playerData.get(next) != null)
                    {
                        if (playerData.get(next).contains(goal))
                        {
                            return false;
                        }
                    }
                } else
                {
                    break;
                }
            }

            if (playerGoals != null)
            {
                playerGoals.add(goal);
                int goalsCompleted = playerGoals.size();
                if (goalsCompleted >= 10)
                {
                    winningPlayer = player;
                    SetState(GameState.ENDING);
                }

                goal.SetWhoClaimedGoal(player);
                plugin.getServer().getOnlinePlayers().forEach(player1 -> player1.playSound(player1.getLocation(),
                        Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f
                ));
                goal.SetCompletion(true);
                AchievementGUI.InitializeItems();
                LoggerUtil.Instance().Broadcast("&aPlayer &b" + player.getName() + " &ahas completed &6" + goal.GetName());
                if (goalsCompleted == 1)
                {
                    player.sendTitle(HexFormat.format("&6" + goal.GetName()), HexFormat.format("&a" + player.getName() + " &fhas completed " + goalsCompleted + " goal"), 20, 80, 20);
                }

                if (goalsCompleted == 5)
                {
                    player.sendTitle(HexFormat.format("&6" + goal.GetName()), HexFormat.format("&a" + player.getName() + " &fhas completed " + goalsCompleted + " goals"), 20, 80, 20);
                }

                if (goalsCompleted == 9)
                {
                    player.sendTitle(HexFormat.format("&6" + goal.GetName()), HexFormat.format("&a" + player.getName() + " &fhas completed " + goalsCompleted + " goals"), 20, 80, 20);
                }
            }
        }
        return true;
    }

    public static List<Goal> pickNRandom(List<Goal> lst, int n) {
        List<Goal> copy = new ArrayList<Goal>(lst);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);

    }


    public void StartMonitorInventoryTask()
    {
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getServer().getOnlinePlayers().forEach(player -> {
                    for (ItemStack itemStack : player.getInventory().getContents())
                    {
                        if (itemStack != null)
                        {
                            for (Goal goal : GameManager.GetInstance().GetActiveGoalList())
                            {
                                if (goal instanceof CollectItemGoal)
                                {
                                    CollectItemGoal collectItemGoal = (CollectItemGoal) goal;
                                    if (collectItemGoal.HasCorrectItem(itemStack.getType()))
                                    {
                                        GameManager.GetInstance().TryClaimGoal(player, goal);
                                    }
                                }

                                if (goal instanceof CollectItemSetGoal)
                                {
                                    CollectItemSetGoal collectItemSetGoal = (CollectItemSetGoal) goal;
                                    if (collectItemSetGoal.HasCorrectItems(player.getInventory()))
                                    {
                                        GameManager.GetInstance().TryClaimGoal(player, goal);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }.runTaskTimer(plugin, 0, 60);
    }

    public void StartGameEndingTask()
    {
        new BukkitRunnable()
        {
            int timer = 0;
            @Override
            public void run()
            {
                switch (timer)
                {
                    case 0:
                    case 4:
                    case 9:
                    case 14:
                        SpawnFireworks();
                        DoThunder();
                        LoggerUtil.Instance().Broadcast("&6GAME ENDING!");
                        LoggerUtil.Instance().Broadcast("&6Winner: " + "&a" + winningPlayer.getName());
                        break;

                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        LoggerUtil.Instance().Broadcast("&cServer Shutdown in " + (20 - timer) + "...");
                        break;

                    case 19:
                        SpawnFireworks();
                        DoThunder();
                        LoggerUtil.Instance().Broadcast("&cServer Shutdown in " + (20 - timer) + "...");
                        break;

                    case 20:
                        LoggerUtil.Instance().Broadcast("&cServer shutting down.");
                        cancel();
                        ResetPlayers(true);
                        Bukkit.shutdown();
                }
                timer++;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void SetWinner(Player winner)
    {
        winningPlayer = winner;
    }

    private void TeleportPlayersToSpawn()
    {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        });
    }

    private void ResetPlayers(boolean resetToSurvivalMode)
    {
        Bukkit.getServer().getOnlinePlayers().forEach( player -> {
            if (resetToSurvivalMode)
            {
                player.setGameMode(GameMode.SURVIVAL);
            }
            player.getActivePotionEffects().clear();
            for (PotionEffect effect : player.getActivePotionEffects())
            {
                player.removePotionEffect(effect.getType());
            }
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setSaturation(5);

            player.getInventory().addItem(ItemUtil.Instance.GetTrackingCompass());
        });
    }

    private void SwitchPlayersToSpectator()
    {
        PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1);
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            player.addPotionEffect(nightVision);
            player.setGameMode(GameMode.SPECTATOR);
        });
        TeleportPlayersToSpawn();
        ResetPlayers(false);
    }

    private void DoThunder()
    {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f);
        });
    }

    private void SpawnFireworks()
    {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            Firework firework = (Firework) Bukkit.getServer().getWorld("world").spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            meta.setPower(3);
            firework.setFireworkMeta(meta);
        });
    }

    private void StartCompassTrackerTask()
    {
        new BukkitRunnable() {
            @Override
            public void run()
            {
                Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                    UUID closestPlayerUid = GetClosestPlayer(player.getLocation(), player.getUniqueId());

                    if (closestPlayerUid != null) {
                        Location closestPlayerLoc = GetPlayerFromUid(closestPlayerUid).getLocation();
                        player.setCompassTarget(closestPlayerLoc);
                    }
                });
            }
        }.runTaskTimer(plugin, 0, 40);
    }

    private UUID GetClosestPlayer(Location loc, UUID exceptPlayerId) {
        if (Objects.requireNonNull(loc.getWorld()).getEnvironment() != World.Environment.NORMAL) {
            return null;
        }
        UUID closestPlayer = null;
        double distanceToClosestPlayer = 0.0D;
        double xLoc = loc.getX();
        double yLoc = loc.getY();

        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            if (player.getUniqueId() != exceptPlayerId && player.getGameMode() != GameMode.SPECTATOR) {
                double p2xLoc = player.getLocation().getX();
                double p2yLoc = player.getLocation().getY();
                double distance = Math.sqrt((p2yLoc - yLoc) * (p2yLoc - yLoc) + (p2xLoc - xLoc) * (p2xLoc - xLoc));

                if (closestPlayer == null) {
                    distanceToClosestPlayer = distance;
                    closestPlayer = player.getUniqueId();
                } else {
                    if (distance < distanceToClosestPlayer) {
                        distanceToClosestPlayer = distance;
                        closestPlayer = player.getUniqueId();
                    }
                }
            }
        }

        return closestPlayer;
    }

    private Player GetPlayerFromUid(UUID uid) {
        return plugin.getServer().getPlayer(uid);
    }

    public int GetPlayersInGame()
    {
        return playerData.size();
    }
}

