package de.protokollmensch.ezsyncchat.config;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getWebhookUrl() {
        return plugin.getConfig().getString("webhook-url");
    }

    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("enabled", true);
    }
}
