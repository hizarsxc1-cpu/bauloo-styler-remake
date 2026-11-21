package dev.hizarsxc1.bauloo;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public final class VisualEffects {
    private VisualEffects() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            int age = client.player.age;
            double x = client.player.getX();
            double y = client.player.getY();
            double z = client.player.getZ();

            if (StylerConfig.isEnabled("Crystal") && age % 6 == 0)
                emit(client, "Crystal", age, x, y + 1.15, z, 0.42, 0.02,
                        ParticleTypes.ENCHANT, ParticleTypes.END_ROD, ParticleTypes.WITCH, ParticleTypes.CRIT);
            if (StylerConfig.isEnabled("Anchor") && age % 9 == 0)
                emit(client, "Anchor", age, x, y + 0.18, z, 0.48, 0.025,
                        ParticleTypes.PORTAL, ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.REVERSE_PORTAL, ParticleTypes.END_ROD);
            if (StylerConfig.isEnabled("Armor") && age % 8 == 0)
                emit(client, "Armor", age, x, y + 0.9, z, 0.58, 0.015,
                        ParticleTypes.CRIT, ParticleTypes.HAPPY_VILLAGER, ParticleTypes.ENCHANT, ParticleTypes.TOTEM_OF_UNDYING);
            if (StylerConfig.isEnabled("Tools") && age % 10 == 0)
                emit(client, "Tools", age, x, y + 0.72, z, 0.38, 0.01,
                        ParticleTypes.ELECTRIC_SPARK, ParticleTypes.CRIT, ParticleTypes.FLAME, ParticleTypes.END_ROD);
            if (StylerConfig.isEnabled("Fire") && age % 8 == 0 && StylerConfig.styleIndex("Fire") != 3)
                emit(client, "Fire", age, x, y + 0.12, z, 0.38, 0.018,
                        ParticleTypes.FLAME, ParticleTypes.SMOKE, ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.ASH);
            if (StylerConfig.isEnabled("Sky") && age % 12 == 0)
                emit(client, "Sky", age, x, y + 2.0, z, 0.5, 0.006,
                        ParticleTypes.CLOUD, ParticleTypes.ASH, ParticleTypes.END_ROD, ParticleTypes.ENCHANT);

            if (StylerConfig.isEnabled("Glowstone") && age % 10 == 0)
                emit(client, "Glowstone", age, x, y + 0.6, z, 0.55, 0.01,
                        ParticleTypes.END_ROD, ParticleTypes.HAPPY_VILLAGER, ParticleTypes.WITCH, ParticleTypes.ENCHANT);
            if (StylerConfig.isEnabled("Deepslate") && age % 14 == 0)
                emit(client, "Deepslate", age, x, y + 0.25, z, 0.52, 0.004,
                        ParticleTypes.ASH, ParticleTypes.SMOKE, ParticleTypes.PORTAL, ParticleTypes.REVERSE_PORTAL);
            if (StylerConfig.isEnabled("Grass") && age % 12 == 0)
                emit(client, "Grass", age, x, y + 0.4, z, 0.6, 0.008,
                        ParticleTypes.HAPPY_VILLAGER, ParticleTypes.COMPOSTER, ParticleTypes.NOTE, ParticleTypes.CRIT);
            if (StylerConfig.isEnabled("Inventory") && age % 15 == 0)
                emit(client, "Inventory", age, x, y + 1.2, z, 0.5, 0.006,
                        ParticleTypes.ENCHANT, ParticleTypes.END_ROD, ParticleTypes.CRIT, ParticleTypes.HAPPY_VILLAGER);
            if (StylerConfig.isEnabled("Pickaxe") && age % 9 == 0)
                emit(client, "Pickaxe", age, x, y + 0.7, z, 0.42, 0.012,
                        ParticleTypes.ELECTRIC_SPARK, ParticleTypes.CRIT, ParticleTypes.END_ROD, ParticleTypes.FLAME);
            if (StylerConfig.isEnabled("Snow") && age % 11 == 0)
                emit(client, "Snow", age, x, y + 1.0, z, 0.62, -0.012,
                        ParticleTypes.SNOWFLAKE, ParticleTypes.WHITE_ASH, ParticleTypes.CLOUD, ParticleTypes.ASH);
            if (StylerConfig.isEnabled("Stone") && age % 14 == 0)
                emit(client, "Stone", age, x, y + 0.3, z, 0.5, 0.003,
                        ParticleTypes.CRIT, ParticleTypes.ASH, ParticleTypes.SMOKE, ParticleTypes.PORTAL);
            if (StylerConfig.isEnabled("Sword") && age % 8 == 0)
                emit(client, "Sword", age, x, y + 0.85, z, 0.46, 0.012,
                        ParticleTypes.SWEEP_ATTACK, ParticleTypes.CRIT, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.END_ROD);

            if (StylerConfig.isEnabled("Extras") && age % 16 == 0)
                emit(client, "Extras", age, x, y + 1.45, z, 0.62, 0.008,
                        ParticleTypes.HAPPY_VILLAGER, ParticleTypes.NOTE, ParticleTypes.CRIT, ParticleTypes.WITCH);
            if (StylerConfig.isEnabled("Cosmetics") && age % 4 == 0)
                emit(client, "Cosmetics", age, x, y + 0.85, z, 0.72, 0.012,
                        ParticleTypes.END_ROD, ParticleTypes.ENCHANT, ParticleTypes.HEART, ParticleTypes.TOTEM_OF_UNDYING);
        });
    }

    private static void emit(MinecraftClient client, String category, int age,
                             double x, double y, double z, double radius, double velocityY,
                             ParticleEffect... choices) {
        int selected = Math.floorMod(StylerConfig.styleIndex(category), choices.length);
        ParticleEffect particle = choices[selected];
        double angle = age * (0.16 + selected * 0.055);
        double px = x + Math.cos(angle) * radius;
        double pz = z + Math.sin(angle) * radius;
        double py = y + Math.sin(angle * 1.7) * (0.035 + selected * 0.018);
        client.world.addParticle(particle, px, py, pz, 0.0, velocityY, 0.0);
    }
}
