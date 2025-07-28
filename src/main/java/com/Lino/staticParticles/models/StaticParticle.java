package com.Lino.staticParticles.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class StaticParticle implements ConfigurationSerializable {

    private final Location location;
    private final String effectName;

    public StaticParticle(Location location, String effectName) {
        this.location = location;
        this.effectName = effectName;
    }

    public Location getLocation() {
        return location;
    }

    public String getEffectName() {
        return effectName;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", location.getWorld().getName());
        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());
        map.put("effect", effectName);
        return map;
    }

    public static StaticParticle deserialize(Map<String, Object> map) {
        String worldName = (String) map.get("world");
        double x = ((Number) map.get("x")).doubleValue();
        double y = ((Number) map.get("y")).doubleValue();
        double z = ((Number) map.get("z")).doubleValue();
        String effect = (String) map.get("effect");

        Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
        return new StaticParticle(location, effect);
    }
}