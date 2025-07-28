package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class MagicRingsEffect extends ParticleEffect {

    @Override
    public void display(Location location) {
        int rings = 3;
        double baseRadius = 1.0;

        for (int ring = 0; ring < rings; ring++) {
            double radius = baseRadius + ring * 0.5;
            double height = location.getY() + ring * 0.8;
            double angleOffset = ring * Math.PI / rings + tick * 0.02;
            int particles = 16 + ring * 4;

            for (int i = 0; i < particles; i++) {
                double angle = (2 * Math.PI * i / particles) + angleOffset;
                double x = location.getX() + radius * Math.cos(angle);
                double z = location.getZ() + radius * Math.sin(angle);

                Location particleLocation = new Location(location.getWorld(), x, height, z);

                if (i % 2 == 0) {
                    spawnParticle(particleLocation, Particle.WITCH, 0, 0, 0, 0, 1);
                } else {
                    spawnParticle(particleLocation, Particle.PORTAL, 0.1, 0.1, 0.1, 0, 2);
                }
            }
        }

        if (tick % 20 == 0) {
            spawnParticle(location.clone().add(0, 2, 0), Particle.ENCHANT, 0.5, 0.5, 0.5, 0.1, 20);
        }
    }
}