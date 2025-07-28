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

        String effectName = args[0].toLowerCase();

        if (effectName.equals("remove")) {
            if (!player.hasPermission("staticparticles.remove")) {
                player.sendMessage(messages.getMessage("no-permission"));
                return true;
            }

            double radius = 5.0;
            if (args.length > 1) {
                try {
                    radius = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(messages.getMessage("invalid-radius"));
                    return true;
                }
            }

            int removed = particleManager.removeNearbyParticles(player.getLocation(), radius);
            player.sendMessage(messages.getMessage("particles-removed").replace("{count}", String.valueOf(removed)));
            return true;
        }

        if (effectName.equals("list")) {
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

        if (effectName.equals("clear")) {
            if (!player.hasPermission("staticparticles.clear")) {
                player.sendMessage(messages.getMessage("no-permission"));
                return true;
            }

            int count = particleManager.clearAllParticles();
            player.sendMessage(messages.getMessage("all-particles-cleared").replace("{count}", String.valueOf(count)));
            return true;
        }

        ParticleEffect effect = particleManager.getEffect(effectName);
        if (effect == null) {
            player.sendMessage(messages.getMessage("invalid-effect"));
            player.sendMessage(messages.getMessage("available-effects")
                    .replace("{effects}", String.join(", ", particleManager.getEffectNames())));
            return true;
        }

        StaticParticle particle = new StaticParticle(player.getLocation(), effectName);
        particleManager.addParticle(particle);

        player.sendMessage(messages.getMessage("particle-created")
                .replace("{effect}", effectName)
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
            completions.add("list");
            completions.add("clear");

            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            return List.of("5", "10", "15", "20");
        }

        return new ArrayList<>();
    }
}