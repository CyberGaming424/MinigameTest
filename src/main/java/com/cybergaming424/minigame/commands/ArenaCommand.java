package com.cybergaming424.minigame.commands;

import com.cybergaming424.minigame.GameState;
import com.cybergaming424.minigame.MiniGame;
import com.cybergaming424.minigame.instance.Arena;
import com.cybergaming424.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private MiniGame miniGame;

    public ArenaCommand(MiniGame miniGame) {
        this.miniGame = miniGame;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 1 && args[0].equalsIgnoreCase("list")){
                player.sendMessage(ChatColor.GREEN + "These are the available arenas:");
                for(Arena arena : miniGame.getArenaManager().getArenas()){
                    player.sendMessage(ChatColor.GREEN + "- " + arena.getId() +
                            "(" + arena.getState().name().toLowerCase() + ")");
                }
            }else if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
                Arena arena = miniGame.getArenaManager().getArena(player);
                if(arena != null){
                    player.sendMessage(ChatColor.RED + "You have left the arena");
                    arena.removePlayer(player);
                }else{
                    player.sendMessage(ChatColor.RED + "You are not in an arena!");
                }
            }else if(args.length == 2 && args[0].equalsIgnoreCase("join")){
                if(miniGame.getArenaManager().getArena(player) != null){
                    player.sendMessage(ChatColor.RED + " You are already in an arena!");
                    return false;
                }

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "This was an invalid arena ID!");
                    return false;
                }

                if(id >= 0 && id < miniGame.getArenaManager().getArenas().size() - 1){
                    Arena arena = miniGame.getArenaManager().getArena(id);
                    if(arena.getState() == GameState.RECRUITING ||
                            (arena.getState() == GameState.COUTNDOWN && arena.getPlayers().size() < ConfigManager.getRequiredPlayer())){
                        player.sendMessage(ChatColor.GREEN + "You are now playing in Arena " + id + ".");
                        arena.addPlayer(player);
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "This was an invalid arena ID!");
                    return false;
                }

            }else{
                player.sendMessage(ChatColor.RED + "Invalid usage! These are the options:"+
                        "\n- /arena list"+
                        "\n- /arena join <ID>"+
                        "\n- /arena leave");
            }
        }

        return false;
    }
}
