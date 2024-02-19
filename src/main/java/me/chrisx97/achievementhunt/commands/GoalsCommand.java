package me.chrisx97.achievementhunt.commands;

import me.chrisx97.achievementhunt.utils.AchievementGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoalsCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args)
    {
        Player player = (Player) sender;
        if (player != null)
        {
            AchievementGUI.OpenGUI(player);
            return true;
        }
        return false;
    }
}
