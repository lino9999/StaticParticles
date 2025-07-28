package com.Lino.staticParticles.effects.impl;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class CrystalShardsEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        int shards = 5;
        for (int i = 0; i < shards; i++) {
            double angle = (2 * Math.PI * i) / shards + tick * 0.01;
            double yOffset = Math.sin(tick * 0.05 + i) * 0.3;
            Location loc = rotateAroundY(location.clone().add(0, 1.5 + yOffset, 0), 1.0, angle);
            Particle.DustOptions crystal = new Particle.DustOptions(
                    Color.fromRGB(150, 200, 255), 1.5f
            );
            spawnParticle(loc, Particle.DUST, 0, 0, 0, 0, 1, crystal);
            spawnParticle(loc, Particle.END_ROD, 0, 0, 0, 0, 1);
        }
    }
}