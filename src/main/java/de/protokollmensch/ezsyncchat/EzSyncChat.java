package de.protokollmensch.ezsyncchat;

import de.protokollmensch.ezsyncchat.commands.EzSyncChatCommand;
import de.protokollmensch.ezsyncchat.commands.EzSyncChatTabCompleter;
import de.protokollmensch.ezsyncchat.config.ConfigManager;
import de.protokollmensch.ezsyncchat.listeners.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EzSyncChat extends JavaPlugin {

    private static EzSyncChat instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        getServer().getPluginManager().registerEvents(new ChatListener(configManager), this);

        Objects.requireNonNull(getCommand("ezsyncchat")).setExecutor(new EzSyncChatCommand(this));
        Objects.requireNonNull(getCommand("ezsyncchat")).setTabCompleter(new EzSyncChatTabCompleter());

        getLogger().info("§b[EzSyncChat] Plugin successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(" ");
        getLogger().info("§c[EzSyncChat] Plugin has been disabled. See you next time!");
        getLogger().info(" ");
    }

    public static EzSyncChat getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}