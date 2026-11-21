# Bauloo Styler Remake

An independent Fabric client mod for Minecraft Java **1.21.0**. This project is implemented from scratch and does not include the original mod's code or assets.

## Current build: 0.3.0
- Press **G** to open the settings menu.
- Eight independent categories: Crystal, Anchor, Armor, Tools, Fire, Sky, Extras, and Cosmetics.
- Every category has an ON/OFF control and a selectable visual style.
- Style selection is saved to `config/bauloo-styler-remake.json`.
- Selected styles now change the category's client-side particle effect.
- The menu does not pause single-player.
- Effects are intentionally low-frequency for mobile performance.

## Style choices
- Crystal: Classic, Aqua, Violet, Invisible
- Anchor: Classic, Blue Pulse, Purple Glow, Soft Light
- Armor: Classic, Emerald, Sakura, Obsidian
- Tools: Classic, Electric, Frost, Ember
- Fire: Classic, Low Flame, Blue Flame, Off
- Sky: Classic, Cloudy, Aurora, Night
- Extras: Classic, Spark, Soft, Minimal
- Cosmetics: Classic, Orbit, Halo, Stardust

These are original lightweight visual presets. This build is a foundation: full block/item texture replacement, skybox packs, armor models, and cosmetic models still need separate implementation.

## Build
Requires Java 21 and Gradle 8.10.2.

```sh
gradle clean build
```
