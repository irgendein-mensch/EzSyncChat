package de.protokollmensch.ezsyncchat.utils;

import de.protokollmensch.ezsyncchat.EzSyncChat;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebhookUtil {

    public static void sendPlayerMessage(String webhookUrl, String playerName, String message) {
        String avatarUrl = "https://mc-heads.net/avatar/" + playerName;
        sendWebhook(webhookUrl, playerName, avatarUrl, "**" + message);
    }

    public static void sendSystemMessage(String webhookUrl, String message) {
        String avatarUrl = "https://cdn.discordapp.com/avatars/712759741528408064/d3ee14343f8a927473f68a8e9e0e0e29.webp?size=1024&format=webp&width=640&height=640";
        sendWebhook(webhookUrl, "EzSyncChat", avatarUrl, message);
    }

    private static void sendWebhook(String webhookUrl, String username, String avatarUrl, String content) {
        if (webhookUrl == null || webhookUrl.isEmpty()) return;

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = String.format("{" +
                    "\"username\":\"%s\"," +
                    "\"avatar_url\":\"%s\"," +
                    "\"content\":\"%s\"" +
                    "}",
                    username.replace("\"", "\\\""),
                    avatarUrl.replace("\"", "\\\""),
                    content.replace("\"", "\\\""));

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204 && responseCode != 200) {
                EzSyncChat.getInstance().getLogger().warning("Error when sending to Discord: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}