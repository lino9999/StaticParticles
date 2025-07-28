package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class HeartPulseEffect extends ParticleEffect {

    @Override
    public void display(Location location) {
        double scale = 0.1 + Math.abs(Math.sin(tick * 0.05)) * 0.05;
        int points = 30;

        for (int i = 0; i < points; i++) {
            double t = (2 * Math.PI * i) / points;

            double x = 16 * Math.pow(Math.sin(t), 3);
            double z = 13 * Math.cos(t) - 5 * Math.cos(2*t) - 2 * Math.cos(3*t) - Math.cos(4*t);

            x *= scale;
            z *= scale;

            Location heartLocation = location.clone().add(x, 1.5, z);
            spawnParticle(heartLocation, Particle.HEART, 0, 0, 0, 0, 1);
        }

        if (tick % 20 == 0) {
            spawnParticle(location.clone().add(0, 2, 0), Particle.HEART, 0.5, 0.5, 0.5, 0.1, 10);
        }
    }
}