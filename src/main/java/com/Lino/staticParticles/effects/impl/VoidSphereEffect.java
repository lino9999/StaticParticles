package com.Lino.staticParticles.effects.impl;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class VoidSphereEffect extends ParticleEffect {

    @Override
    public void display(Location location) {
        double radius = 1.5;
        int particles = 30;

        for (int i = 0; i < particles; i++) {
            double u = Math.random();
            double v = Math.random();

            double theta = 2 * Math.PI * u;
            double phi = Math.acos(2 * v - 1);

            double x = radius * Math.sin(phi) * Math.cos(theta);
            double y = radius * Math.sin(phi) * Math.sin(theta);
            double z = radius * Math.cos(phi);

            Location particleLocation = location.clone().add(x, y + 1.5, z);

            Particle.DustOptions purple = new Particle.DustOptions(Color.fromRGB(75, 0, 130), 0.8f);
            spawnParticle(particleLocation, Particle.DUST, 0, 0, 0, 0, 1, purple);

            if (Math.random() < 0.1) {
                spawnParticle(particleLocation, Particle.PORTAL, 0.1, 0.1, 0.1, 0, 1);
            }
        }

        double angle = tick * 0.05;
        for (int i = 0; i < 3; i++) {
            double ringAngle = angle + (i * 2 * Math.PI / 3);
            double ringX = location.getX() + radius * 1.2 * Math.cos(ringAngle);
            double ringZ = location.getZ() + radius * 1.2 * Math.sin(ringAngle);
            Location ringLocation = new Location(location.getWorld(), ringX, location.getY() + 1.5, ringZ);

            spawnParticle(ringLocation, Particle.WITCH, 0, 0, 0, 0, 1);
        }
    }
}