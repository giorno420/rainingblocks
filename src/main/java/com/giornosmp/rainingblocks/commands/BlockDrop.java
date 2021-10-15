package com.giornosmp.rainingblocks.commands;

import com.giornosmp.rainingblocks.RainingBlocks;
import com.giornosmp.rainingblocks.game.RainManager;
import com.giornosmp.rainingblocks.gamestates.BlockStage;
import com.giornosmp.rainingblocks.gamestates.State;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class BlockDrop implements CommandExecutor {

    private final RainingBlocks mainInstance;


    public BlockDrop(RainingBlocks mainInstance) {
        this.mainInstance = mainInstance;
    }

    /*
    * added these comments because
    * code was nearly unreadable
    */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        int delay = mainInstance.getConfig().getInt("delay");


        // when the sender is console 😳
        if (!(sender instanceof Player) && label.equalsIgnoreCase("rainingblocks")) {
            System.out.println("Only players can use this command");
            return true;
        }

        else if (sender instanceof Player){
            Player player = (Player) sender;


            // when the command is /rainingblocks 😳
            if (label.equalsIgnoreCase("rainingblocks")){


                // rains the blocks
                new RainManager(mainInstance).runTaskTimer(mainInstance, 1, 1);
                new RainManager(mainInstance).runTaskTimer(mainInstance, 1, 1);


                // when the argument is not null 😳
                if (args.length > 0){


                    // when the switch statement checks if the subcommand is either reload, start or stop 😳
                    switch(args[0]){
                        case "reload":
                            mainInstance.reloadConfig();
                            player.sendRawMessage("§a§lReloaded RainingBlocks configuration!");
                            break;
                        case "start":
                            if (mainInstance.getGameState() == State.OFF){
                                mainInstance.setGameState(State.ON);
                                mainInstance.setblockStage(BlockStage.STAGE1);
                                String stage1 = mainInstance.getConfig().getString("stages.stage1.message");
                                // method to make blocks rain
                                Bukkit.broadcastMessage("§6§lLet them rain.");
                                showTitle(player, stage1);
                                Bukkit.broadcastMessage(stage1);
//                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a timer 20 20 10");
//                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle {\"text\": \""+mainInstance.getConfig().getString("stages.stage1.message")+"\", \"color\": \"yellow\"");
//                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"\"");
                                player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1.0F, 1);
                                new BukkitRunnable(){

                                    @Override
                                    public void run() {
                                        if (mainInstance.getGameState() == State.ON){
                                            Bukkit.broadcastMessage(Objects.requireNonNull(mainInstance.getConfig().getString("stages.stage2.message")));
                                            showTitle(player, mainInstance.getConfig().getString("stages.stage2.message"));
                                            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1.0F, 1);
                                            mainInstance.setblockStage(BlockStage.STAGE2);
                                            new BukkitRunnable(){

                                                @Override
                                                public void run() {
                                                    if (mainInstance.getGameState() == State.ON) {
                                                        Bukkit.broadcastMessage(Objects.requireNonNull(mainInstance.getConfig().getString("stages.stage3.message")));
                                                        showTitle(player, mainInstance.getConfig().getString("stages.stage3.message"));
                                                        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1.0F, 1);
                                                        mainInstance.setblockStage(BlockStage.STAGE3);
                                                    }
                                                    else if (mainInstance.getGameState() == State.OFF){
                                                        this.cancel();
                                                    }
                                                }
                                            }.runTaskLater(mainInstance, mainInstance.getConfig().getInt("stages.stage2.time") * 20L);
                                        }
                                        else if (mainInstance.getGameState() == State.OFF){
                                            this.cancel();
                                        }
                                    }
                                }.runTaskLater(mainInstance, mainInstance.getConfig().getInt("stages.stage1.time") * 20L);

                            }
                            else if (mainInstance.getGameState() == State.ON){
                                player.sendRawMessage("§cThe game has already started!");
                            }
                            break;
                        case "stop":
                            if (mainInstance.getGameState() == State.OFF){
                                player.sendRawMessage("§cThe game is already stopped!");
                            }
                            else if (mainInstance.getGameState() == State.ON){
                                mainInstance.setGameState(State.OFF);
                                mainInstance.setblockStage(BlockStage.STAGE1);
                                Bukkit.broadcastMessage("§a§lThe block rain has been stopped!");
                            }
                            break;
                    }

                }


                // when the argument is 0 😳
                else if (args.length == 0){
                    player.sendRawMessage("Usage:");
                    player.sendRawMessage("/rainingblocks reload");
                    player.sendRawMessage("/rainingblocks start");
                    player.sendRawMessage("/rainingblocks start");
                }
            }
        }


        return true;
    }
    private void showTitle(Player player, String message){
        player.sendTitle(ChatColor.RED + "WARNING", ChatColor.YELLOW + "" + ChatColor.BOLD + message, 10, 20, 10);
    }
}
