package de.protokollmensch.ezsyncchat.commands;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class EzSyncChatCommand implements CommandExecutor {

    private final EzSyncChat plugin;

    public EzSyncChatCommand(EzSyncChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ezsyncchat.admin")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "[EzSyncChat] Configuration reloaded.");
            }
            case "webhook" -> {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Please provide a webhook URL.");
                    return true;
                }
                String url = args[1];
                plugin.getConfig().set("webhook-url", url);
                plugin.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "[EzSyncChat] Webhook URL updated.");
            }
            case "toggle" -> {
                boolean current = plugin.getConfig().getBoolean("enabled", true);
                plugin.getConfig().set("enabled", !current);
                plugin.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "[EzSyncChat] Chat sync is now " + (current ? "§cdisabled" : "§aenabled") + ChatColor.GREEN + ".");
            }
            default -> {
                sender.sendMessage(ChatColor.RED + "Unknown command. Use /ezsyncchat help");
            }
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "===== EzSyncChat Help =====");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat reload" + ChatColor.GRAY + " - Reloads the configuration.");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat webhook <URL>" + ChatColor.GRAY + " - Sets the Discord webhook URL.");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat toggle" + ChatColor.GRAY + " - Enables/Disables the sync.");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat help" + ChatColor.GRAY + " - Shows this help.");
    }
}
