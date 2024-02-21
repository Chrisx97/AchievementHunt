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

            if (args.length == 1)
            {
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

            if (args.length == 2)
            {
                if (args[0].equalsIgnoreCase("start"))
                {
                    if (!player.isOp()) return false;

                    if (args[1].equalsIgnoreCase("normal"))
                    {
                        GameManager.GetInstance().SetState(GameState.ACTIVE);
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("lightning"))
                    {
                        GameManager.GetInstance().SetLightningRound();
                        GameManager.GetInstance().SetState(GameState.ACTIVE);
                        return true;
                    }
                }
            }
        }

        player.sendMessage("Usage: /achievementhunt start (normal | lightning) | stop | goals");
        return false;
    }
}
