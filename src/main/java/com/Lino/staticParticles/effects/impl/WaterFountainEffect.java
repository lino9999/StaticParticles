package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class WaterFountainEffect extends ParticleEffect {

    @Override
    public void display(Location location) {
        int streams = 8;
        double maxHeight = 3.0;
        double speed = 0.3;

        for (int i = 0; i < streams; i++) {
            double angle = (2 * Math.PI * i) / streams;
            double time = (tick * 0.05) % 2.0;

            double velocityX = Math.cos(angle) * speed * 0.5;
            double velocityZ = Math.sin(angle) * speed * 0.5;
            double velocityY = speed * 2 - time;

            if (velocityY > 0) {
                double height = time * velocityY;
                double distance = time * speed * 0.5;

                double x = location.getX() + Math.cos(angle) * distance;
                double z = location.getZ() + Math.sin(angle) * distance;
                double y = location.getY() + height;

                Location particleLocation = new Location(location.getWorld(), x, y, z);
                spawnParticle(particleLocation, Particle.SPLASH, 0, 0, 0, 0, 1);
                spawnParticle(particleLocation, Particle.BUBBLE, 0.1, 0.1, 0.1, 0, 2);
            }
        }

        spawnParticle(location, Particle.BUBBLE_COLUMN_UP, 0.2, 0, 0.2, 0.1, 3);
    }
}