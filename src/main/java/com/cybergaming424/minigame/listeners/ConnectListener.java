package com.cybergaming424.minigame.listeners;

import com.cybergaming424.minigame.GameState;
import com.cybergaming424.minigame.MiniGame;
import com.cybergaming424.minigame.instance.Arena;
import com.cybergaming424.minigame.manager.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {
    private MiniGame miniGame;

    public ConnectListener(MiniGame miniGame) {
        this.miniGame = miniGame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(ConfigManager.getLobbySpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Arena arena = miniGame.getArenaManager().getArena(e.getPlayer());
        if(arena != null){
            arena.removePlayer(e.getPlayer());
        }

    }

}
