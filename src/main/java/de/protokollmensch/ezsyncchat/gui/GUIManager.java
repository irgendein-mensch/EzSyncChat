package de.protokollmensch.ezsyncchat.gui;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class GUIManager {
    private final EzSyncChat plugin;

    public GUIManager(EzSyncChat plugin) {
        this.plugin = plugin;
    }

    public void openSettingsGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "§bEzSyncChat Settings");

        ItemStack webhookItem = createGuiItem(Material.PAPER,
                "§aWebhook URL",
                "§7Current: " + (plugin.getConfig().getString("webhook-url", "Not set").isEmpty() ? "Not set" : "§aSet"),
                "§eClick to change");

        ItemStack iconItem = createGuiItem(Material.PAINTING,
                "§aServer Icon URL",
                "§7Current: " + (plugin.getConfig().getString("server-icon-url", "Not set").isEmpty() ? "Not set" : "§aSet"),
                "§eClick to change");

        boolean enabled = plugin.getConfig().getBoolean("enabled", true);
        ItemStack toggleItem = createGuiItem(enabled ? Material.LIME_DYE : Material.GRAY_DYE,
                enabled ? "§aEnabled" : "§cDisabled",
                "§7Click to toggle");

        ItemStack closeItem = createGuiItem(Material.BARRIER, "§cClose", "§7Click to close");

        gui.setItem(11, webhookItem);
        gui.setItem(13, toggleItem);
        gui.setItem(15, iconItem);
        gui.setItem(26, closeItem);

        player.openInventory(gui);
    }

    private ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}