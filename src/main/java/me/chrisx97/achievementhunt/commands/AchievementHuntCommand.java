package me.chrisx97.achievementhunt.commands;

import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.game.GameState;
import me.chrisx97.achievementhunt.utils.AchievementGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AchievementHuntCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        Player player = (Player) sender;
        if (player != null)
        {
            if (args.length == 0)
            {
                player.sendMessage("Usage: /achievementhunt start | stop | goals");
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start"))
                {
                    if (!player.isOp()) return false;

                    GameManager.GetInstance().SetState(GameState.ACTIVE);
                    return true;
                }

                if (args[0].equalsIgnoreCase("stop"))
                {
                    if (!player.isOp()) return false;

                    GameManager.GetInstance().SetWinner(player);
                    GameManager.GetInstance().SetState(GameState.ENDING);
                    return true;

                }

                if (args[0].equalsIgnoreCase("goals"))
                {
                    AchievementGUI.OpenGUI(player);
                }
            }
        }
        return false;
    }
}
