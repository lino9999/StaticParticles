package com.Lino.staticParticles.effects.impl;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import com.Lino.staticParticles.effects.ParticleEffect;

class EnchantCircleEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double radius = 2.0;
        int particles = 20;
        for (int i = 0; i < particles; i++) {
            double angle = (2 * Math.PI * i) / particles + tick * 0.02;
            Location loc = rotateAroundY(location.clone().add(0, 0.5, 0), radius, angle);
            spawnParticle(loc, Particle.ENCHANT, 0, 0.2, 0, 0, 3);
        }
    }
}

class PortalVortexEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double height = 3.0;
        int spirals = 2;
        for (int y = 0; y < 20; y++) {
            for (int s = 0; s < spirals; s++) {
                double angle = (y * 0.3) + (s * Math.PI) + tick * 0.05;
                double radius = 0.5 + y * 0.05;
                Location loc = rotateAroundY(location.clone().add(0, y * 0.15, 0), radius, angle);
                spawnParticle(loc, Particle.PORTAL, 0, 0, 0, 0, 1);
            }
        }
    }
}

class StarFieldEffect extends ParticleEffect {
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

class FlameTornadoEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        double maxHeight = 4.0;
        for (int y = 0; y < 30; y++) {
            double height = y * 0.13;
            double radius = 0.3 + (height / maxHeight) * 0.7;
            double angle = height * 2 + tick * 0.1;
            Location loc = rotateAroundY(location.clone().add(0, height, 0), radius, angle);
            spawnParticle(loc, Particle.FLAME, 0, 0, 0, 0.01, 1);
            if (y % 3 == 0) spawnParticle(loc, Particle.SMOKE, 0, 0.1, 0, 0, 1);
        }
    }
}

class IceCrownEffect extends ParticleEffect {
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

class GoldenAuraEffect extends ParticleEffect {
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

class NatureBloomEffect extends ParticleEffect {
    @Override
    public void display(Location location) {
        if (tick % 10 == 0) {
            for (int i = 0; i < 5; i++) {
                double angle = Math.random() * Math.PI * 2;
                double radius = Math.random() * 2;
                Location loc = rotateAroundY(location, radius, angle);
                spawnParticle(loc, Particle.CHERRY_LEAVES, 0.3, 0.3, 0.3, 0, 3);
                if (Math.random() < 0.3) spawnParticle(loc, Particle.HAPPY_VILLAGER, 0, 0, 0, 0, 1);
            }
        }
    }
}

class ElectricCageEffect extends ParticleEffect {
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

class CrystalShardsEffect extends ParticleEffect {
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