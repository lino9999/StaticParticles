package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class EnchantCircleEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double radius = 2.0;
        int particles = 20;
        for (int i = 0; i < particles; i++) {
            double angle = (2 * Math.PI * i) / particles + tick * 0.02;
            Location loc = rotateAroundY(location.clone().add(0, 0.5, 0), radius, angle);
            spawnParticle(loc, Particle.ENCHANT, 0, 0.2, 0, 0, 3);
        }
    }
}