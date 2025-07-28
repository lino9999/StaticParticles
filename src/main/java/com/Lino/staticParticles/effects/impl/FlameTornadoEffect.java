package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class FlameTornadoEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double maxHeight = 4.0;
        for (int y = 0; y < 30; y++) {
            double height = y * 0.13;
            double radius = 0.3 + (height / maxHeight) * 0.7;
            double angle = height * 2 + tick * 0.1;
            Location loc = rotateAroundY(location.clone().add(0, height, 0), radius, angle);
            spawnParticle(loc, Particle.FLAME, 0, 0, 0, 0.01, 1);
            if (y % 3 == 0) spawnParticle(loc, Particle.SMOKE, 0, 0.1, 0, 0, 1);
        }
    }
}