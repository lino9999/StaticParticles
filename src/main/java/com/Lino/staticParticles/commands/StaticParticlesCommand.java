package com.Lino.staticParticles.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import com.Lino.staticParticles.StaticParticles;
import com.Lino.staticParticles.effects.ParticleEffect;
import com.Lino.staticParticles.managers.MessageManager;
import com.Lino.staticParticles.managers.ParticleManager;
import com.Lino.staticParticles.models.StaticParticle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaticParticlesCommand implements CommandExecutor, TabCompleter {

    private final StaticParticles plugin;
    private final MessageManager messages;
    private final ParticleManager particleManager;

    public StaticParticlesCommand(StaticParticles plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessageManager();
        this.particleManager = plugin.getParticleManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("staticparticles.use")) {
            player.sendMessage(messages.getMessage("no-permission"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(messages.getMessage("usage"));
            player.sendMessage(messages.getMessage("available-effects")
                    .replace("{effects}", String.join(", ", particleManager.getEffectNames())));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("remove")) {
            if (!player.hasPermission("staticparticles.remove")) {
                player.sendMessage(messages.getMessage("no-permission"));
                return true;
            }

            if (args.length > 1) {

                try {
                    int value = Integer.parseInt(args[1]);

                    // If value is small (0-99), treat as ID
                    if (value < 100 && value >= 0) {
                        List<StaticParticle> particles = particleManager.getParticles();
                        if (value < particles.size()) {
                            StaticParticle particle = particles.get(value);
                            boolean removed = particleManager.removeParticle(value);
                            if (removed) {
                                player.sendMessage(messages.getMessage("particle-removed-id")
                                        .replace("{id}", String.valueOf(value))
                                        .replace("{effect}", particle.getEffectName()));
                            } else {
                                player.sendMessage(messages.getMessage("particle-not-found"));
                            }
                        } else {
                            player.sendMessage(messages.getMessage("invalid-particle-id"));
                        }
                    } else {
                        // Treat as radius
                        double radius = value;
                        int removed = particleManager.removeNearbyParticles(player.getLocation(), radius);
                        player.sendMessage(messages.getMessage("particles-removed").replace("{count}", String.valueOf(removed)));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(messages.getMessage("invalid-number"));
                    return true;
                }
            } else {
                // Default radius
                int removed = particleManager.removeNearbyParticles(player.getLocation(), 5.0);
                player.sendMessage(messages.getMessage("particles-removed").replace("{count}", String.valueOf(removed)));
            }
            return true;
        }

        if (subCommand.equals("removeid")) {
            if (!player.hasPermission("staticparticles.remove")) {
                player.sendMessage(messages.getMessage("no-permission"));
                return true;
            }

            if (args.length < 2) {
                player.sendMessage(messages.getMessage("usage-removeid"));
                return true;
            }

            try {
                int id = Integer.parseInt(args[1]);
                List<StaticParticle> particles = particleManager.getParticles();

                if (id >= 0 && id < particles.size()) {
                    StaticParticle particle = particles.get(id);
                    boolean removed = particleManager.removeParticle(id);
                    if (removed) {
                        player.sendMessage(messages.getMessage("particle-removed-id")
                                .replace("{id}", String.valueOf(id))
                                .replace("{effect}", particle.getEffectName()));
                    } else {
                        player.sendMessage(messages.getMessage("particle-not-found"));
                    }
                } else {
                    player.sendMessage(messages.getMessage("invalid-particle-id"));
                }
            } catch (NumberFormatException e) {
                player.sendMessage(messages.getMessage("invalid-number"));
            }
            return true;
        }

        if (subCommand.equals("list")) {
            player.sendMessage(messages.getMessage("particle-list-header"));
            List<StaticParticle> particles = particleManager.getParticles();
            if (particles.isEmpty()) {
                player.sendMessage(messages.getMessage("no-particles"));
            } else {
                for (int i = 0; i < particles.size(); i++) {
                    StaticParticle particle = particles.get(i);
                    player.sendMessage(messages.getMessage("particle-list-entry")
                            .replace("{id}", String.valueOf(i))
                            .replace("{effect}", particle.getEffectName())
                            .replace("{world}", particle.getLocation().getWorld().getName())
                            .replace("{x}", String.valueOf((int)particle.getLocation().getX()))
                            .replace("{y}", String.valueOf((int)particle.getLocation().getY()))
                            .replace("{z}", String.valueOf((int)particle.getLocation().getZ())));
                }
            }
            return true;
        }

        if (subCommand.equals("clear")) {
            if (!player.hasPermission("staticparticles.clear")) {
                player.sendMessage(messages.getMessage("no-permission"));
                return true;
            }

            int count = particleManager.clearAllParticles();
            player.sendMessage(messages.getMessage("all-particles-cleared").replace("{count}", String.valueOf(count)));
            return true;
        }

        ParticleEffect effect = particleManager.getEffect(subCommand);
        if (effect == null) {
            player.sendMessage(messages.getMessage("invalid-effect"));
            player.sendMessage(messages.getMessage("available-effects")
                    .replace("{effects}", String.join(", ", particleManager.getEffectNames())));
            return true;
        }

        StaticParticle particle = new StaticParticle(player.getLocation(), subCommand);
        particleManager.addParticle(particle);

        player.sendMessage(messages.getMessage("particle-created")
                .replace("{effect}", subCommand)
                .replace("{x}", String.valueOf((int)player.getLocation().getX()))
                .replace("{y}", String.valueOf((int)player.getLocation().getY()))
                .replace("{z}", String.valueOf((int)player.getLocation().getZ())));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.addAll(particleManager.getEffectNames());
            completions.add("remove");
            completions.add("removeid");
            completions.add("list");
            completions.add("clear");

            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                return List.of("5", "10", "15", "20");
            }
            if (args[0].equalsIgnoreCase("removeid")) {
                List<String> ids = new ArrayList<>();
                for (int i = 0; i < particleManager.getParticles().size(); i++) {
                    ids.add(String.valueOf(i));
                }
                return ids;
            }
        }

        return new ArrayList<>();
    }
}