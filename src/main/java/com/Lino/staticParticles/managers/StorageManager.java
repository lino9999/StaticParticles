package com.Lino.staticParticles.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.Lino.staticParticles.StaticParticles;
import com.Lino.staticParticles.models.StaticParticle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageManager {

    private final StaticParticles plugin;
    private final File dataFile;
    private FileConfiguration dataConfig;

    public StorageManager(StaticParticles plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "particles.yml");
        loadDataFile();
    }

    private void loadDataFile() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveParticles() {
        List<Map<String, Object>> serializedParticles = new ArrayList<>();
        for (StaticParticle particle : plugin.getParticleManager().getParticles()) {
            serializedParticles.add(particle.serialize());
        }

        dataConfig.set("particles", serializedParticles);

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadParticles() {
        List<StaticParticle> particles = new ArrayList<>();

        if (dataConfig.contains("particles")) {
            List<Map<?, ?>> serializedParticles = dataConfig.getMapList("particles");
            for (Map<?, ?> map : serializedParticles) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> particleMap = (Map<String, Object>) map;
                    StaticParticle particle = StaticParticle.deserialize(particleMap);
                    particles.add(particle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        plugin.getParticleManager().setParticles(particles);
    }
}