package com.Lino.staticParticles.managers;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import com.Lino.staticParticles.StaticParticles;
import com.Lino.staticParticles.effects.ParticleEffect;
import com.Lino.staticParticles.effects.impl.*;
import com.Lino.staticParticles.models.StaticParticle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParticleManager {

    private final StaticParticles plugin;
    private final Map<String, ParticleEffect> effects;
    private final List<StaticParticle> particles;
    private BukkitTask particleTask;

    public ParticleManager(StaticParticles plugin) {
        this.plugin = plugin;
        this.effects = new ConcurrentHashMap<>();
        this.particles = Collections.synchronizedList(new ArrayList<>());

        registerEffects();
    }

    private void registerEffects() {
        registerEffect("flame_spiral", new FlameSpiralEffect());
        registerEffect("water_fountain", new WaterFountainEffect());
        registerEffect("enchant_circle", new EnchantCircleEffect());
        registerEffect("rainbow_helix", new RainbowHelixEffect());
        registerEffect("portal_vortex", new PortalVortexEffect());
        registerEffect("heart_pulse", new HeartPulseEffect());
        registerEffect("star_field", new StarFieldEffect());
        registerEffect("magic_rings", new MagicRingsEffect());
        registerEffect("flame_tornado", new FlameTornadoEffect());
        registerEffect("ice_crown", new IceCrownEffect());
        registerEffect("void_sphere", new VoidSphereEffect());
        registerEffect("golden_aura", new GoldenAuraEffect());
        registerEffect("nature_bloom", new NatureBloomEffect());
        registerEffect("electric_cage", new ElectricCageEffect());
        registerEffect("crystal_shards", new CrystalShardsEffect());
    }

    private void registerEffect(String name, ParticleEffect effect) {
        effects.put(name.toLowerCase(), effect);
    }

    public void startParticleTask() {
        if (particleTask != null) {
            particleTask.cancel();
        }

        particleTask = new BukkitRunnable() {
            @Override
            public void run() {
                synchronized (particles) {
                    for (StaticParticle particle : particles) {
                        ParticleEffect effect = effects.get(particle.getEffectName());
                        if (effect != null) {
                            effect.display(particle.getLocation());
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    public void stopParticleTask() {
        if (particleTask != null) {
            particleTask.cancel();
            particleTask = null;
        }
    }

    public void addParticle(StaticParticle particle) {
        particles.add(particle);
        plugin.getStorageManager().saveParticles();
    }

    public int removeNearbyParticles(Location center, double radius) {
        int removed = 0;
        synchronized (particles) {
            Iterator<StaticParticle> iterator = particles.iterator();
            while (iterator.hasNext()) {
                StaticParticle particle = iterator.next();
                if (particle.getLocation().getWorld().equals(center.getWorld()) &&
                        particle.getLocation().distance(center) <= radius) {
                    iterator.remove();
                    removed++;
                }
            }
        }
        plugin.getStorageManager().saveParticles();
        return removed;
    }

    public int clearAllParticles() {
        int count = particles.size();
        particles.clear();
        plugin.getStorageManager().saveParticles();
        return count;
    }

    public ParticleEffect getEffect(String name) {
        return effects.get(name.toLowerCase());
    }

    public Set<String> getEffectNames() {
        return new TreeSet<>(effects.keySet());
    }

    public List<StaticParticle> getParticles() {
        return new ArrayList<>(particles);
    }

    public void setParticles(List<StaticParticle> loadedParticles) {
        particles.clear();
        particles.addAll(loadedParticles);
    }
}