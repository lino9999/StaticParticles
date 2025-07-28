package com.Lino.staticParticles.effects.impl;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

public class GoldenAuraEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double radius = 1.5;
        for (int i = 0; i < 20; i++) {
            double angle = Math.random() * Math.PI * 2;
            double r = Math.random() * radius;
            double height = Math.random() * 2;
            Location loc = rotateAroundY(location.clone().add(0, height, 0), r, angle);
            Particle.DustOptions gold = new Particle.DustOptions(Color.fromRGB(255, 215, 0), 1.2f);
            spawnParticle(loc, Particle.DUST, 0, 0, 0, 0, 1, gold);
        }
    }
}