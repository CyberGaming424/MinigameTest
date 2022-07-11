package com.cybergaming424.minigame.listeners;

import com.cybergaming424.minigame.GameState;
import com.cybergaming424.minigame.MiniGame;
import com.cybergaming424.minigame.instance.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private MiniGame miniGame;

    public GameListener(MiniGame miniGame) {
        this.miniGame = miniGame;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Arena arena = miniGame.getArenaManager().getArena(e.getPlayer());

        if(arena != null && arena.getState().equals(GameState.LIVE)){
                arena.getGame().addPoint(e.getPlayer());
        }
    }
}
