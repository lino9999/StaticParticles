package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class StarFieldEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        for (int i = 0; i < 15; i++) {
            double offset = 2.0;
            Location starLoc = location.clone().add(
                    (Math.random() - 0.5) * offset * 2,
                    Math.random() * 3,
                    (Math.random() - 0.5) * offset * 2
            );
            spawnParticle(starLoc, Particle.END_ROD, 0, 0, 0, 0, 1);
        }
    }
}