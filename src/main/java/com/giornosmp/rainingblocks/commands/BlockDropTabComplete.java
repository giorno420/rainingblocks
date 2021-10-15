package com.giornosmp.rainingblocks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class BlockDropTabComplete implements TabCompleter {

    List<String> parameters = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (parameters.isEmpty()){
            parameters.add("start");
            parameters.add("stop");
            parameters.add("reload");
        }

        List<String> result = new ArrayList<String>();

        if (args.length == 1){
            for (String argss : parameters){
                if (argss.toLowerCase().startsWith(args[0].toLowerCase())) result.add(argss);
            }
            return result;
        }

        return null;
    }
}
