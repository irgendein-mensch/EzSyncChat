package de.protokollmensch.ezsyncchat.listeners;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import de.protokollmensch.ezsyncchat.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {
    private final EzSyncChat plugin;
    private final GUIManager guiManager;

    public GUIListener(EzSyncChat plugin, GUIManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§bEzSyncChat Settings")) return;
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        int slot = event.getRawSlot();

        switch (slot) {
            case 11:
                handleWebhookClick(player);
                break;
            case 13:
                handleToggleClick(player);
                break;
            case 15:
                handleIconClick(player);
                break;
            case 26:
                player.closeInventory();
                break;
        }
    }

    private void handleWebhookClick(Player player) {
        player.closeInventory();
        plugin.getConfig().set("enabled", false);
        plugin.saveConfig();

        plugin.getChatInputManager().awaitInput(
            player,
            "§aPlease enter the new webhook URL:",
            url -> {
                plugin.getConfig().set("webhook-url", url);
                plugin.getConfig().set("enabled", true);
                plugin.saveConfig();
                player.sendMessage("§aWebhook URL updated!");
                guiManager.openSettingsGUI(player);
            },
            () -> {
                plugin.getConfig().set("enabled", true);
                plugin.saveConfig();
            },
            30
        );
    }

    private void handleToggleClick(Player player) {
        boolean current = plugin.getConfig().getBoolean("enabled", true);
        plugin.getConfig().set("enabled", !current);
        plugin.saveConfig();
        String status = current ? "§cdisabled" : "§aenabled";
        player.sendMessage("§aChat sync is now " + status + "§a.");
        guiManager.openSettingsGUI(player);
    }

    private void handleIconClick(Player player) {
        player.closeInventory();
        plugin.getConfig().set("enabled", false);
        plugin.saveConfig();

        plugin.getChatInputManager().awaitInput(
            player,
            "§aPlease enter the new server icon URL:",
            url -> {
                plugin.getConfig().set("server-icon-url", url);
                plugin.getConfig().set("enabled", true);
                plugin.saveConfig();
                player.sendMessage("§aServer icon URL updated!");
                guiManager.openSettingsGUI(player);
            },
            () -> {
                plugin.getConfig().set("enabled", true);
                plugin.saveConfig();
            },
            30
        );
    }
}