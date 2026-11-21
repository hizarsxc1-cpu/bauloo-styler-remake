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
            "Crystal", "Anchor", "Armor", "Tools", "Fire", "Sky",
            "Glowstone", "Deepslate", "Grass", "Inventory", "Pickaxe", "Snow",
            "Stone", "Sword", "Extras", "Cosmetics"
    };
    private static final String[][] STYLES = {
            {"Classic", "Aqua", "Violet", "Invisible"},
            {"Classic", "Blue Pulse", "Purple Glow", "Soft Light"},
            {"Classic", "Emerald", "Sakura", "Obsidian"},
            {"Classic", "Electric", "Frost", "Ember"},
            {"Classic", "Low Flame", "Blue Flame", "Off"},
            {"Classic", "Cloudy", "Aurora", "Night"},
            {"Classic", "Aqua Plus", "Blueberry", "Purple"},
            {"Classic", "Black", "Ice", "Lavender"},
            {"Classic", "Lush", "Lime", "Dark"},
            {"Classic", "Amaranth", "Blossom", "Doge"},
            {"Classic", "Clear Vision", "Lavender", "Marlow"},
            {"Classic", "Ice", "Blue", "Purple"},
            {"Classic", "Black", "White", "Red"},
            {"Classic", "Lavender", "Blue", "Doge"},
            {"Classic", "Spark", "Soft", "Minimal"},
            {"Classic", "Orbit", "Halo", "Stardust"}
    };
    private static final Map<String, Boolean> ENABLED = new LinkedHashMap<>();
    private static final Map<String, Integer> SELECTED = new LinkedHashMap<>();

    static {
        for (String category : CATEGORIES) {
            ENABLED.put(category, false);
            SELECTED.put(category, 0);
        }
    }

    private StylerConfig() {}

    public static String[] categories() { return CATEGORIES.clone(); }
    public static boolean isEnabled(String category) { return ENABLED.getOrDefault(category, false); }
    public static int styleIndex(String category) { return SELECTED.getOrDefault(category, 0); }
    public static String[] styles(String category) {
        int index = categoryIndex(category);
        return index < 0 ? new String[]{"Classic"} : STYLES[index].clone();
    }
    public static void selectStyle(String category, int styleIndex) {
        int index = categoryIndex(category);
        if (index < 0) return;
        SELECTED.put(category, Math.floorMod(styleIndex, STYLES[index].length));
        save();
    }

    public static String styleName(String category) {
        int index = categoryIndex(category);
        if (index < 0) return "Classic";
        String[] choices = STYLES[index];
        return choices[Math.floorMod(styleIndex(category), choices.length)];
    }

    public static void toggle(String category) {
        ENABLED.put(category, !isEnabled(category));
        save();
    }

    public static void cycleStyle(String category) {
        int index = categoryIndex(category);
        if (index < 0) return;
        SELECTED.put(category, (styleIndex(category) + 1) % STYLES[index].length);
        save();
    }

    private static int categoryIndex(String category) {
        for (int i = 0; i < CATEGORIES.length; i++) {
            if (CATEGORIES[i].equals(category)) return i;
        }
        return -1;
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try (Reader reader = Files.newBufferedReader(FILE)) {
            ConfigData data = GSON.fromJson(reader, ConfigData.class);
            if (data != null) {
                if (data.enabled != null) {
                    for (String category : CATEGORIES) {
                        ENABLED.put(category, data.enabled.getOrDefault(category, false));
                    }
                }
                if (data.styles != null) {
                    for (String category : CATEGORIES) {
                        int max = STYLES[categoryIndex(category)].length;
                        SELECTED.put(category, Math.floorMod(data.styles.getOrDefault(category, 0), max));
                    }
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
                GSON.toJson(new ConfigData(ENABLED, SELECTED), writer);
            }
        } catch (Exception ignored) {
            // Config failures must not crash the game.
        }
    }

    private static final class ConfigData {
        private Map<String, Boolean> enabled;
        private Map<String, Integer> styles;
        private ConfigData(Map<String, Boolean> enabled, Map<String, Integer> styles) {
            this.enabled = new LinkedHashMap<>(enabled);
            this.styles = new LinkedHashMap<>(styles);
        }
    }
}
