package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class ElectricCageEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double radius = 2.0;
        int bars = 6;
        for (int i = 0; i < bars; i++) {
            double angle = (2 * Math.PI * i) / bars;
            for (int h = 0; h < 15; h++) {
                if (Math.random() < 0.7) {
                    Location loc = rotateAroundY(location.clone().add(0, h * 0.2, 0), radius, angle);
                    spawnParticle(loc, Particle.ELECTRIC_SPARK, 0, 0, 0, 0, 1);
                }
            }
        }
    }
}