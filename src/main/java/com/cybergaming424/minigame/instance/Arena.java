package com.cybergaming424.minigame.instance;

import com.cybergaming424.minigame.GameState;
import com.cybergaming424.minigame.MiniGame;
import com.cybergaming424.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;

    private MiniGame miniGame;
    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    public Arena(MiniGame miniGame, int id, Location spawn){
        this.id = id;
        this.spawn = spawn;

        this.miniGame = miniGame;
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(this.miniGame, this);
        this.game = new Game(this);
    }

    /* GAME */

    public void start(){ game.start(); }

    public void reset(boolean kickPlayers){
        if(kickPlayers){
            Location loc = ConfigManager.getLobbySpawn();
            for(UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(loc);
            }
            players.clear();
        }

        sendTitle("", "");

        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(miniGame, this);
        game = new Game(this);
    }

    /* TOOLS */

    public void sendMessage(String message){
        for(UUID uuid : players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle){
        for(UUID uuid : players){
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    /* PLAYERS */

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if(state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayer()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("", "");

        switch (state){
            case COUTNDOWN:
                if(players.size() < ConfigManager.getRequiredPlayer()){
                    sendMessage(ChatColor.RED + "There is not enough player. Countdown has stopped!");
                    reset(false);
                    return;
                }
                break;

            case LIVE:
                if(players.size() < ConfigManager.getRequiredPlayer()){
                    sendMessage(ChatColor.RED + "Game has ended as too many players have left!");
                    reset(false);
                }
                break;
        }
    }

    /* INFO */

    public int getId() {
        return id;
    }

    public GameState getState() {
        return state;
    }
    public List<UUID> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
