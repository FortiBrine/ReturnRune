package me.fortibrine.returnrune.message;

import me.fortibrine.returnrune.ReturnRunePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {

    private final ReturnRunePlugin plugin = ReturnRunePlugin.getInstance();
    private final File configFile;
    private final YamlConfiguration config;

    public MessageManager() {
        this.configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String parseMessage(String path, Map<String, String> args) {
        String message = config.getString(path);

        if (message == null) return null;
        String coloredMessage = colorMessage(message);

        for (Map.Entry<String, String> entry : args.entrySet()) {
            String placeholder = entry.getKey();
            String value = entry.getValue();

            if (value == null) continue;

            coloredMessage = coloredMessage.replace("%" + placeholder, value);
        }

        return coloredMessage;
    }

    public String parseMessage(Message messageType, Map<String, String> args) {
        return parseMessage(messageType.getPath(), args);
    }

    public String parseMessage(Message messageType) {
        return parseMessage(messageType, new HashMap<>());
    }

    public void sendMessage(CommandSender sender, Message messageType, Map<String, String> args) {
        String message = parseMessage(messageType.getPath(), args);

        if (message == null) return;

        sender.sendMessage(message);
    }

    public void sendMessage(CommandSender sender, Message messageType) {
        sendMessage(sender, messageType, new HashMap<>());
    }

    public String colorMessage(String from) {
        if (from == null) return null;

        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group().substring(2);
            StringBuilder replacement = new StringBuilder();
            for (char c : hexCode.toCharArray()) {
                replacement.append("&").append(c);
            }
            matcher.appendReplacement(result, replacement.toString());
        }
        matcher.appendTail(result);

        return ChatColor.translateAlternateColorCodes('&', result.toString());
    }

    public boolean reloadConfig() {
        try {
            config.load(configFile);
            return true;
        } catch (IOException | InvalidConfigurationException e) {
            return false;
        }
    }

}
