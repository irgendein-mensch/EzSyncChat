package de.protokollmensch.ezsyncchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class EzSyncChatTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("ezsyncchat.admin")) {
                completions.add("reload");
                completions.add("webhook");
                completions.add("icon");
                completions.add("toggle");
                completions.add("help");
            }
        }
        return completions;
    }
}