package de.protokollmensch.ezsyncchat.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatInputManager {
    private final JavaPlugin plugin;
    private final Map<UUID, PendingInput> pendingInputs = new HashMap<>();

    public ChatInputManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isWaitingForInput(Player player) {
        return pendingInputs.containsKey(player.getUniqueId());
    }

    public void awaitInput(Player player, String prompt, Consumer<String> callback, Runnable cancelCallback, int timeoutSeconds) {
        player.sendMessage(prompt);
        player.sendMessage("§7You have §e" + timeoutSeconds + " seconds§7 to respond. Type 'cancel' to abort.");

        UUID playerId = player.getUniqueId();
        pendingInputs.put(playerId, new PendingInput(callback, cancelCallback, System.currentTimeMillis() + timeoutSeconds * 1000L));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (pendingInputs.remove(playerId) != null) {
                player.sendMessage("§cInput timed out.");
                cancelCallback.run();
            }
        }, timeoutSeconds * 20L);
    }

    public void processInput(Player player, String message) {
        PendingInput input = pendingInputs.remove(player.getUniqueId());
        if (input == null) return;

        if (message.equalsIgnoreCase("cancel")) {
            player.sendMessage("§cInput cancelled.");
            input.cancelCallback.run();
            return;
        }

        if (System.currentTimeMillis() > input.expiryTime) {
            player.sendMessage("§cInput timed out.");
            input.cancelCallback.run();
            return;
        }

        input.callback.accept(message);
    }

    private static class PendingInput {
        final Consumer<String> callback;
        final Runnable cancelCallback;
        final long expiryTime;

        PendingInput(Consumer<String> callback, Runnable cancelCallback, long expiryTime) {
            this.callback = callback;
            this.cancelCallback = cancelCallback;
            this.expiryTime = expiryTime;
        }
    }
}