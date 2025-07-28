package com.Lino.staticParticles.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public abstract class ParticleEffect {

    protected static long tick = 0;

    static {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    tick++;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    public abstract void display(Location location);

    protected void spawnParticle(Location location, Particle particle, double offsetX, double offsetY, double offsetZ, double speed, int count) {
        World world = location.getWorld();
        if (world != null) {
            world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, speed, null);
        }
    }

    protected void spawnParticle(Location location, Particle particle, double offsetX, double offsetY, double offsetZ, double speed, int count, Object data) {
        World world = location.getWorld();
        if (world != null) {
            world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, speed, data);
        }
    }

    protected Location rotateAroundY(Location center, double radius, double angle) {
        double x = center.getX() + radius * Math.cos(angle);
        double z = center.getZ() + radius * Math.sin(angle);
        return new Location(center.getWorld(), x, center.getY(), z);
    }
}