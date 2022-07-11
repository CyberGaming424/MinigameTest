package com.cybergaming424.minigame;

import com.cybergaming424.minigame.commands.ArenaCommand;
import com.cybergaming424.minigame.listeners.ConnectListener;
import com.cybergaming424.minigame.listeners.GameListener;
import com.cybergaming424.minigame.manager.ArenaManager;
import com.cybergaming424.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniGame extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        ConfigManager.setUpConfig(this);
        arenaManager = new ArenaManager(this);

        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    public ArenaManager getArenaManager() { return arenaManager; }

}
