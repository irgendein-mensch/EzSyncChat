package de.protokollmensch.ezsyncchat.listeners;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import de.protokollmensch.ezsyncchat.config.ConfigManager;
import de.protokollmensch.ezsyncchat.utils.WebhookUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {
    private final ConfigManager config;

    public ChatListener(ConfigManager config) {
        this.config = config;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!config.isEnabled()) return;

        String playerName = event.getPlayer().getName();
        String message = event.getMessage();

        Bukkit.getScheduler().runTaskAsynchronously(EzSyncChat.getInstance(), () -> {
            WebhookUtil.sendPlayerMessage(config.getWebhookUrl(), playerName, message);
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        if (!config.isEnabled()) return;

        String playerName = event.getPlayer().getName();
        Bukkit.getScheduler().runTaskAsynchronously(EzSyncChat.getInstance(), () -> {
            WebhookUtil.sendSystemMessage(config.getWebhookUrl(), "**" + playerName + " left the game**");
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        if (!config.isEnabled()) return;

        String playerName = event.getPlayer().getName();
        Bukkit.getScheduler().runTaskAsynchronously(EzSyncChat.getInstance(), () -> {
            WebhookUtil.sendSystemMessage(config.getWebhookUrl(), "**" + playerName + " joined the game**");
        });
    }
}