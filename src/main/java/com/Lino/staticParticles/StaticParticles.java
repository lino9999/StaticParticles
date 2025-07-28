package com.Lino.staticParticles;

import org.bukkit.plugin.java.JavaPlugin;
import com.Lino.staticParticles.commands.StaticParticlesCommand;
import com.Lino.staticParticles.managers.MessageManager;
import com.Lino.staticParticles.managers.ParticleManager;
import com.Lino.staticParticles.managers.StorageManager;

import java.io.File;

public class StaticParticles extends JavaPlugin {

    private static StaticParticles instance;
    private MessageManager messageManager;
    private ParticleManager particleManager;
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        instance = this;

        try {
            // Create data folder if it doesn't exist
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            saveDefaultConfig();

            // Save default messages.yml
            if (!new File(getDataFolder(), "messages.yml").exists()) {
                saveResource("messages.yml", false);
            }

            messageManager = new MessageManager(this);
            storageManager = new StorageManager(this);
            particleManager = new ParticleManager(this);

            storageManager.loadParticles();
            particleManager.startParticleTask();

            getCommand("staticparticles").setExecutor(new StaticParticlesCommand(this));

            getLogger().info("StaticParticles v" + getDescription().getVersion() + " has been enabled successfully!");
            getLogger().info("Created by " + getDescription().getAuthors().get(0));
        } catch (Exception e) {
            getLogger().severe("Failed to enable StaticParticles!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (particleManager != null) {
            particleManager.stopParticleTask();
        }

        if (storageManager != null) {
            storageManager.saveParticles();
        }

        getLogger().info("StaticParticles has been disabled!");
    }

    public static StaticParticles getInstance() {
        return instance;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public ParticleManager getParticleManager() {
        return particleManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}