package com.cybergaming424.minigame.instance;

import com.cybergaming424.minigame.GameState;
import com.cybergaming424.minigame.MiniGame;
import com.cybergaming424.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private MiniGame miniGame;
    private Arena arena;
    private int countDownSeconds;

    public Countdown(MiniGame miniGame, Arena arena){
        this.miniGame = miniGame;
        this.arena = arena;
        this.countDownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start(){
        arena.setState(GameState.COUTNDOWN);
        runTaskTimer(miniGame, 0, 20);
    }

    @Override
    public void run() {
        if(countDownSeconds == 0){
            cancel();
            arena.start();
            return;
        }

        if(countDownSeconds <= 10 || countDownSeconds % 15 == 0){
            arena.sendMessage(ChatColor.GREEN + "Game will start in " + countDownSeconds +
                    " second" + (countDownSeconds == 1 ? "" : "s") + ".");
        }

        arena.sendTitle(ChatColor.GREEN.toString() + countDownSeconds + " seconds" + (countDownSeconds == 1 ? "" : "s"),
                ChatColor.GRAY + "until game starts");

        countDownSeconds--;
    }

}
