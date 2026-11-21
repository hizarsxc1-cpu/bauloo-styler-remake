package dev.hizarsxc1.bauloo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class StylerConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("bauloo-styler-remake.json");
    private static final String[] CATEGORIES = {
            "Crystal", "Anchor", "Armor", "Tools", "Fire", "Sky", "Extras", "Cosmetics"
    };
    private static final Map<String, Boolean> ENABLED = new LinkedHashMap<>();

    static {
        for (String category : CATEGORIES) ENABLED.put(category, false);
    }

    private StylerConfig() {}

    public static String[] categories() { return CATEGORIES.clone(); }
    public static boolean isEnabled(String category) { return ENABLED.getOrDefault(category, false); }

    public static void toggle(String category) {
        ENABLED.put(category, !isEnabled(category));
        save();
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try (Reader reader = Files.newBufferedReader(FILE)) {
            ConfigData data = GSON.fromJson(reader, ConfigData.class);
            if (data != null && data.enabled != null) {
                for (String category : CATEGORIES) {
                    ENABLED.put(category, data.enabled.getOrDefault(category, false));
                }
            }
        } catch (Exception ignored) {
            // Keep safe defaults if the config is missing or malformed.
        }
    }

    private static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) {
                GSON.toJson(new ConfigData(ENABLED), writer);
            }
        } catch (Exception ignored) {
            // Config failures must not crash the game.
        }
    }

    private static final class ConfigData {
        private Map<String, Boolean> enabled;
        private ConfigData(Map<String, Boolean> enabled) { this.enabled = new LinkedHashMap<>(enabled); }
    }
}
