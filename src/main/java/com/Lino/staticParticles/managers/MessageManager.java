package com.Lino.staticParticles.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.Lino.staticParticles.StaticParticles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageManager {

    private final StaticParticles plugin;
    private final File messagesFile;
    private FileConfiguration messagesConfig;

    public MessageManager(StaticParticles plugin) {
        this.plugin = plugin;
        this.messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        loadMessages();
    }

    private void loadMessages() {
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        InputStream defaultStream = plugin.getResource("messages.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            messagesConfig.setDefaults(defaultConfig);
        }
    }

    public String getMessage(String key) {
        String message = messagesConfig.getString(key, "&cMessage not found: " + key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void reloadMessages() {
        loadMessages();
    }
}