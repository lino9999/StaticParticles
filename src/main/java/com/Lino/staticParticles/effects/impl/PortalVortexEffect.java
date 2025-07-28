package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class PortalVortexEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double height = 3.0;
        int spirals = 2;
        for (int y = 0; y < 20; y++) {
            for (int s = 0; s < spirals; s++) {
                double angle = (y * 0.3) + (s * Math.PI) + tick * 0.05;
                double radius = 0.5 + y * 0.05;
                Location loc = rotateAroundY(location.clone().add(0, y * 0.15, 0), radius, angle);
                spawnParticle(loc, Particle.PORTAL, 0, 0, 0, 0, 1);
            }
        }
    }
}