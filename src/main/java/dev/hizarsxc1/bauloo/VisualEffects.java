package dev.hizarsxc1.bauloo;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.particle.ParticleTypes;

public final class VisualEffects {
    private VisualEffects() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            int age = client.player.age;

            // Lightweight orbiting sparkle cosmetic. Kept intentionally sparse for mobile clients.
            if (StylerConfig.isEnabled("Cosmetics") && age % 4 == 0) {
                double angle = age * 0.16;
                double radius = 0.72;
                double x = client.player.getX() + Math.cos(angle) * radius;
                double z = client.player.getZ() + Math.sin(angle) * radius;
                double y = client.player.getY() + 0.85 + Math.sin(angle * 1.7) * 0.12;
                client.world.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.012, 0.0);
            }

            // Subtle flame accents around the player's feet; no gameplay or damage effect.
            if (StylerConfig.isEnabled("Fire") && age % 8 == 0) {
                double angle = age * 0.31;
                double x = client.player.getX() + Math.cos(angle) * 0.38;
                double z = client.player.getZ() + Math.sin(angle) * 0.38;
                double y = client.player.getY() + 0.12;
                client.world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.018, 0.0);
            }
        });
    }
}
