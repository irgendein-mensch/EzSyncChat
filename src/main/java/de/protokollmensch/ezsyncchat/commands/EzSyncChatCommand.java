package de.protokollmensch.ezsyncchat.commands;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if (args.length == 0) {
            if (sender instanceof Player player) {
                plugin.getGUIManager().openSettingsGUI(player);
            } else {
                sendHelp(sender);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("manager")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
                return true;
            }
            plugin.getGUIManager().openSettingsGUI(player);
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
            case "icon" -> {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Please provide an icon URL.");
                    return true;
                }
                String iconUrl = args[1];
                plugin.getConfig().set("server-icon-url", iconUrl);
                plugin.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "[EzSyncChat] Server icon URL updated.");
            }
            default -> sender.sendMessage(ChatColor.RED + "Unknown command. Use /ezsyncchat help");
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "===== EzSyncChat Help =====");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat" + ChatColor.GRAY + " - Opens the settings GUI");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat manager" + ChatColor.GRAY + " - Opens the settings GUI");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat reload" + ChatColor.GRAY + " - Reloads the configuration");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat webhook <URL>" + ChatColor.GRAY + " - Sets the Discord webhook URL");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat icon <URL>" + ChatColor.GRAY + " - Sets the server icon URL");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat toggle" + ChatColor.GRAY + " - Enables/Disables the sync");
        sender.sendMessage(ChatColor.AQUA + "/ezsyncchat help" + ChatColor.GRAY + " - Shows this help");
    }
}