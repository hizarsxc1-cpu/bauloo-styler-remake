package dev.hizarsxc1.bauloo;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public final class VisualEffects {
    private VisualEffects() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            int age = client.player.age;
            double px = client.player.getX();
            double py = client.player.getY();
            double pz = client.player.getZ();

            // Crystal: a small enchantment shimmer near the upper body.
            if (StylerConfig.isEnabled("Crystal") && age % 6 == 0) {
                ring(client, ParticleTypes.ENCHANT, age, px, py + 1.15, pz, 0.42, 0.02);
            }

            // Anchor: restrained portal motes around the feet.
            if (StylerConfig.isEnabled("Anchor") && age % 9 == 0) {
                ring(client, ParticleTypes.PORTAL, age, px, py + 0.18, pz, 0.48, 0.025);
            }

            // Armor: occasional defensive-looking crit sparkles; visual only.
            if (StylerConfig.isEnabled("Armor") && age % 8 == 0) {
                ring(client, ParticleTypes.CRIT, age, px, py + 0.9, pz, 0.58, 0.015);
            }

            // Tools: tiny electric sparks around the hands/waist area.
            if (StylerConfig.isEnabled("Tools") && age % 10 == 0) {
                ring(client, ParticleTypes.ELECTRIC_SPARK, age, px, py + 0.72, pz, 0.38, 0.01);
            }

            // Fire: subtle flame accents around the player's feet.
            if (StylerConfig.isEnabled("Fire") && age % 8 == 0) {
                ring(client, ParticleTypes.FLAME, age, px, py + 0.12, pz, 0.38, 0.018);
            }

            // Sky: a few slow cloud motes above the player's head.
            if (StylerConfig.isEnabled("Sky") && age % 12 == 0) {
                double angle = age * 0.19;
                client.world.addParticle(ParticleTypes.CLOUD,
                        px + Math.cos(angle) * 0.5, py + 2.0,
                        pz + Math.sin(angle) * 0.5, 0.0, 0.006, 0.0);
            }

            // Extras: occasional happy-villager sparkle, kept low frequency.
            if (StylerConfig.isEnabled("Extras") && age % 16 == 0) {
                double angle = age * 0.23;
                client.world.addParticle(ParticleTypes.HAPPY_VILLAGER,
                        px + Math.cos(angle) * 0.62, py + 1.45,
                        pz + Math.sin(angle) * 0.62, 0.0, 0.008, 0.0);
            }

            // Cosmetics: orbiting End Rod sparkle.
            if (StylerConfig.isEnabled("Cosmetics") && age % 4 == 0) {
                double angle = age * 0.16;
                client.world.addParticle(ParticleTypes.END_ROD,
                        px + Math.cos(angle) * 0.72,
                        py + 0.85 + Math.sin(angle * 1.7) * 0.12,
                        pz + Math.sin(angle) * 0.72, 0.0, 0.012, 0.0);
            }
        });
    }

    private static void ring(net.minecraft.client.MinecraftClient client,
                             ParticleEffect particle, int age,
                             double x, double y, double z,
                             double radius, double velocityY) {
        double angle = age * 0.27;
        double px = x + Math.cos(angle) * radius;
        double pz = z + Math.sin(angle) * radius;
        client.world.addParticle(particle, px, y, pz, 0.0, velocityY, 0.0);
    }
}
