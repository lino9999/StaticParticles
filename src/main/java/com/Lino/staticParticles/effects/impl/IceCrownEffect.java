package com.Lino.staticParticles.effects.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class IceCrownEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        int spikes = 8;
        double radius = 1.0;
        for (int i = 0; i < spikes; i++) {
            double angle = (2 * Math.PI * i) / spikes;
            for (int h = 0; h < 10; h++) {
                double height = h * 0.1;
                double spikeRadius = radius * (1 - height);
                Location loc = rotateAroundY(location.clone().add(0, 2 + height, 0), spikeRadius, angle);
                spawnParticle(loc, Particle.SNOWFLAKE, 0, 0, 0, 0, 1);
                if (h % 3 == 0) spawnParticle(loc, Particle.ITEM_SNOWBALL, 0, 0, 0, 0, 1);
            }
        }
    }
}