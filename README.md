# Bauloo Styler Remake

An independent Fabric client mod for Minecraft Java **1.21.0**. This project is implemented from scratch and does not bundle the original mod's code or assets.

## Current features
- Press **G** to open the in-game settings menu.
- Toggle categories: Crystal, Anchor, Armor, Tools, Fire, Sky, Extras, and Cosmetics.
- **Cosmetics ON:** a sparse orbiting End Rod sparkle around the player.
- **Fire ON:** a subtle flame particle accent around the player's feet. It has no damage or gameplay effect.
- Settings are saved in `config/bauloo-styler-remake.json`.
- The menu does not pause single-player.
- Particle effects are deliberately low-frequency to reduce load on mobile clients.

## In progress
The remaining category switches are configuration placeholders. Their independent texture/style effects are not implemented yet.

## Build
Requires Java 21 and Gradle 8.10.2.

```sh
gradle clean build
```
