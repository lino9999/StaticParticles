package com.Lino.staticParticles;

import org.bukkit.plugin.java.JavaPlugin;
import com.Lino.staticParticles.commands.StaticParticlesCommand;
import com.Lino.staticParticles.managers.MessageManager;
import com.Lino.staticParticles.managers.ParticleManager;
import com.Lino.staticParticles.managers.StorageManager;

public class StaticParticles extends JavaPlugin {

    private static StaticParticles instance;
    private MessageManager messageManager;
    private ParticleManager particleManager;
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        messageManager = new MessageManager(this);
        storageManager = new StorageManager(this);
        particleManager = new ParticleManager(this);

        storageManager.loadParticles();
        particleManager.startParticleTask();

        getCommand("staticparticles").setExecutor(new StaticParticlesCommand(this));
    }

    @Override
    public void onDisable() {
        if (particleManager != null) {
            particleManager.stopParticleTask();
        }

        if (storageManager != null) {
            storageManager.saveParticles();
        }
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