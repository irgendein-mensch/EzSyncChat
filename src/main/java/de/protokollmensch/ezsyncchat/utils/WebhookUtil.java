package de.protokollmensch.ezsyncchat.utils;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebhookUtil {

    public static void sendPlayerMessage(String webhookUrl, String playerName, String message) {
        if (!isValidUrl(webhookUrl)) {
            EzSyncChat.getInstance().getLogger().warning("Invalid webhook URL for player message");
            return;
        }

        String avatarUrl = "https://mc-heads.net/avatar/" + playerName;
        sendWebhook(webhookUrl, playerName, avatarUrl, message);
    }

    public static void sendSystemMessage(String webhookUrl, String message) {
        if (!isValidUrl(webhookUrl)) {
            EzSyncChat.getInstance().getLogger().warning("Invalid webhook URL for system message");
            return;
        }

        String avatarUrl = EzSyncChat.getInstance().getConfig().getString("server-icon-url", "");
        sendWebhook(webhookUrl, "EzSyncChat", avatarUrl, message);
    }

    private static void sendWebhook(String webhookUrl, String username, String avatarUrl, String content) {
        if (!isValidUrl(webhookUrl)) {
            EzSyncChat.getInstance().getLogger().warning("Invalid webhook URL: " + webhookUrl);
            return;
        }

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            String jsonPayload = String.format("{" +
                    "\"username\":\"%s\"," +
                    "\"avatar_url\":\"%s\"," +
                    "\"content\":\"%s\"" +
                    "}",
                    escapeJson(username),
                    escapeJson(avatarUrl),
                    escapeJson(content));

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204 && responseCode != 200) {
                EzSyncChat.getInstance().getLogger().warning("Discord webhook error - HTTP " + responseCode);
            }
        } catch (IOException e) {
            EzSyncChat.getInstance().getLogger().warning("Failed to send webhook: " + e.getMessage());
        }
    }

    private static boolean isValidUrl(String url) {
        return url != null && !url.isEmpty() &&
               (url.startsWith("http://") || url.startsWith("https://"));
    }

    private static String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}