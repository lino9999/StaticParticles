package com.Lino.staticParticles.effects.impl;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class RainbowHelixEffect extends ParticleEffect {

    @Override
    public void display(Location location) {
        double helixHeight = 4.0;
        double radius = 1.2;
        int points = 20;

        for (int i = 0; i < points; i++) {
            double progress = (double) i / points;
            double angle = (tick * 0.05 + progress * Math.PI * 4) % (Math.PI * 2);
            double y = progress * helixHeight;

            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);

            int hue = (int) ((tick * 2 + i * 10) % 360);
            Color color = Color.fromRGB(
                    (int) (Math.sin(Math.toRadians(hue)) * 127 + 128),
                    (int) (Math.sin(Math.toRadians(hue + 120)) * 127 + 128),
                    (int) (Math.sin(Math.toRadians(hue + 240)) * 127 + 128)
            );

            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0f);
            Location particleLocation = new Location(location.getWorld(), x, location.getY() + y, z);
            spawnParticle(particleLocation, Particle.DUST, 0, 0, 0, 0, 1, dustOptions);
        }
    }
}