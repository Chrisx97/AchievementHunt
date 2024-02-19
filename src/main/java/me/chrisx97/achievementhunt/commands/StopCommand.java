package me.chrisx97.achievementhunt.commands;

import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {

        if (commandSender.isOp())
        {
            //LoggerUtil.Instance().Broadcast("&6Starting a Game");
            GameManager.GetInstance().SetWinner((Player) commandSender);
            GameManager.GetInstance().SetState(GameState.ENDING);
            return true;
        }
        return false;
    }
}
