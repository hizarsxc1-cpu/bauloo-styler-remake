# Bauloo Styler Remake

An independent Fabric client mod for Minecraft Java **1.21.0**. Implemented from scratch without the original mod's code or assets.

## Current features
- Press **G** to open the in-game settings menu.
- Toggle Crystal, Anchor, Armor, Tools, Fire, Sky, Extras, and Cosmetics independently.
- Crystal: light enchantment shimmer near the upper body.
- Anchor: restrained portal motes around the feet.
- Armor: occasional crit sparkles around the player.
- Tools: tiny electric sparks around the player.
- Fire: subtle flame accents around the feet.
- Sky: slow cloud motes above the player.
- Extras: occasional happy-villager sparkles.
- Cosmetics: orbiting End Rod sparkle.
- Settings save to `config/bauloo-styler-remake.json`.
- The menu does not pause single-player.

## Performance notes
Effects are client-side, visual-only, and deliberately low-frequency to reduce load on mobile clients. Disable any category you do not want.

## Build
Requires Java 21 and Gradle 8.10.2.

```sh
gradle clean build
```
