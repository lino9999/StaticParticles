package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class NatureBloomEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        if (tick % 10 == 0) {
            for (int i = 0; i < 5; i++) {
                double angle = Math.random() * Math.PI * 2;
                double radius = Math.random() * 2;
                Location loc = rotateAroundY(location, radius, angle);
                spawnParticle(loc, Particle.CHERRY_LEAVES, 0.3, 0.3, 0.3, 0, 3);
                if (Math.random() < 0.3) spawnParticle(loc, Particle.HAPPY_VILLAGER, 0, 0, 0, 0, 1);
            }
        }
    }
}