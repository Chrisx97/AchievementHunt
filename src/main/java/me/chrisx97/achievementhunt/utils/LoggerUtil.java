package me.chrisx97.achievementhunt.utils;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil
{
    private static LoggerUtil instance = new LoggerUtil();
    private Logger logger;
    private Plugin plugin;



    public void Initialize(Plugin plugin) {
        this.plugin = plugin;
        this.logger = this.plugin.getLogger();

    }
    public void Log(Object msg) {
        logger.log(Level.INFO, msg.toString());
    }

    public static LoggerUtil Instance()
    {
        return instance;
    }

    public void Broadcast(Object msg)
    {
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers())
        {
            onlinePlayer.sendMessage(HexFormat.format(msg.toString()));
        }
    }
}
