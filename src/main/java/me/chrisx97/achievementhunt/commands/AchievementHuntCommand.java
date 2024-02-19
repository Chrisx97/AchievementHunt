package me.chrisx97.achievementhunt.commands;

import me.chrisx97.achievementhunt.utils.AchievementGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AchievementHuntCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        Player sender = (Player) commandSender;
        if (sender != null)
        {
            AchievementGUI.OpenGUI(sender);
            return true;
        }
        return false;
    }
}
