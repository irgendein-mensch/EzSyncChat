package de.protokollmensch.ezsyncchat.listeners;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final EzSyncChat plugin;

    public PlayerChatListener(EzSyncChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (plugin.getChatInputManager().isWaitingForInput(player)) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(plugin, () ->
                plugin.getChatInputManager().processInput(player, message));
        }
    }
}