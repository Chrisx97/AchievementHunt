package me.chrisx97.achievementhunt.commands;

import me.chrisx97.achievementhunt.game.GameManager;
import me.chrisx97.achievementhunt.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {

        if (commandSender.isOp())
        {
            GameManager.GetInstance().SetState(GameState.ACTIVE);
            return true;
        }
        return false;
    }
}
