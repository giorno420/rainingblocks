package com.giornosmp.rainingblocks.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("no");
            return false;
        }
        else if (sender instanceof Player){
            Player player = (Player) sender;
            String sus = "sus";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \""+sus+"\"");
            return true;
        }
        return false;
    }
}
