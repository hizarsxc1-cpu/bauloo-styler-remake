# Bauloo Styler Remake

An independent Fabric client mod for Minecraft Java **1.21.0**. Implemented from scratch without the original mod's code or assets.

## Current build: 0.5.0
- Press **G** to open the settings menu.
- Sixteen independently configurable style families.
- ON/OFF and four selectable presets per family.
- Settings are saved to `config/bauloo-styler-remake.json`.
- Two-page layout adapts to narrow/mobile screens.
- Selected presets drive lightweight client-side particle accents.
- Menu does not pause single-player.
- Exports four independently-authored 16x16 Glowstone texture packs into the game's `resourcepacks` folder.
- After exporting, enable the chosen `Bauloo Glowstone` pack under Options > Resource Packs, then apply changes.

## Style families
- Crystal: Classic, Aqua, Violet, Invisible
- Anchor: Classic, Blue Pulse, Purple Glow, Soft Light
- Armor: Classic, Emerald, Sakura, Obsidian
- Tools: Classic, Electric, Frost, Ember
- Fire: Classic, Low Flame, Blue Flame, Off
- Sky: Classic, Cloudy, Aurora, Night
- Glowstone: Classic, Aqua Plus, Blueberry, Purple
- Deepslate: Classic, Black, Ice, Lavender
- Grass: Classic, Lush, Lime, Dark
- Inventory: Classic, Amaranth, Blossom, Doge
- Pickaxe: Classic, Clear Vision, Lavender, Marlow
- Snow: Classic, Ice, Blue, Purple
- Stone: Classic, Black, White, Red
- Sword: Classic, Lavender, Blue, Doge
- Extras: Classic, Spark, Soft, Minimal
- Cosmetics: Classic, Orbit, Halo, Stardust

**Scope note:** The Glowstone exporter is the first real texture-pack integration. Other presets still control lightweight visual accents and do not yet replace block/item textures, armor models, skyboxes, or cosmetic models. The original mod's code and assets are not included.

## Build
Requires Java 21 and Gradle 8.10.2.

```sh
gradle clean build
```
