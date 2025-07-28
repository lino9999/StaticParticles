package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class FlameSpiralEffect extends ParticleEffect {

    @Override
    public void display(Location location) {
        double angle = tick * 0.1;
        double radius = 1.5;
        double height = 3.0;
        int spirals = 3;

        for (int i = 0; i < spirals; i++) {
            double spiralAngle = angle + (i * 2 * Math.PI / spirals);
            double y = (tick * 0.05) % height;

            double x = location.getX() + radius * Math.cos(spiralAngle);
            double z = location.getZ() + radius * Math.sin(spiralAngle);

            Location particleLocation = new Location(location.getWorld(), x, location.getY() + y, z);
            spawnParticle(particleLocation, Particle.FLAME, 0, 0, 0, 0, 1);
            spawnParticle(particleLocation, Particle.SMALL_FLAME, 0.1, 0.1, 0.1, 0, 2);
        }
    }
}