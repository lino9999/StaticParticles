package com.Lino.staticParticles.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.Lino.staticParticles.StaticParticles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private final StaticParticles plugin;
    private final File messagesFile;
    private FileConfiguration messagesConfig;
    private final Map<String, String> defaultMessages;

    public MessageManager(StaticParticles plugin) {
        this.plugin = plugin;
        this.messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        this.defaultMessages = new HashMap<>();

        setDefaultMessages();
        loadMessages();
    }

    private void setDefaultMessages() {
        defaultMessages.put("prefix", "&6[StaticParticles] &f");
        defaultMessages.put("only-players", "&cOnly players can use this command!");
        defaultMessages.put("no-permission", "&cYou don't have permission to use this command!");
        defaultMessages.put("usage", "&eUsage: /staticparticles <effect|remove|removeid|list|clear>");
        defaultMessages.put("usage-removeid", "&eUsage: /staticparticles removeid <id>");
        defaultMessages.put("available-effects", "&eAvailable effects: &f{effects}");
        defaultMessages.put("invalid-effect", "&cInvalid effect name!");
        defaultMessages.put("particle-created", "&aParticle effect &e{effect} &acreated at &f{x}, {y}, {z}");
        defaultMessages.put("particles-removed", "&aRemoved &e{count} &aparticles within radius");
        defaultMessages.put("particle-removed-id", "&aRemoved particle &e#{id} &7({effect})");
        defaultMessages.put("particle-not-found", "&cParticle not found!");
        defaultMessages.put("invalid-particle-id", "&cInvalid particle ID!");
        defaultMessages.put("invalid-radius", "&cInvalid radius! Please enter a number.");
        defaultMessages.put("invalid-number", "&cInvalid number!");
        defaultMessages.put("particle-list-header", "&e=== Static Particles List ===");
        defaultMessages.put("particle-list-entry", "&7[{id}] &e{effect} &7at &f{world} {x}, {y}, {z}");
        defaultMessages.put("no-particles", "&7No particles found.");
        defaultMessages.put("all-particles-cleared", "&aCleared all &e{count} &aparticles!");
    }

    private void loadMessages() {
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        boolean needsSave = false;
        for (Map.Entry<String, String> entry : defaultMessages.entrySet()) {
            if (!messagesConfig.contains(entry.getKey())) {
                messagesConfig.set(entry.getKey(), entry.getValue());
                needsSave = true;
            }
        }

        if (needsSave) {
            try {
                messagesConfig.save(messagesFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getMessage(String key) {
        String message = messagesConfig.getString(key);

        if (message == null) {
            message = defaultMessages.getOrDefault(key, "&cMessage not found: " + key);

            // Aggiungi il messaggio mancante al file
            messagesConfig.set(key, message);
            try {
                messagesConfig.save(messagesFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String prefix = messagesConfig.getString("prefix", "");
        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

    public void reloadMessages() {
        loadMessages();
    }
}