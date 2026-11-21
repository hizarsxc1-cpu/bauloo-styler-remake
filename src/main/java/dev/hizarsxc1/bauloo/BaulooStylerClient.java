package dev.hizarsxc1.bauloo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class BaulooStylerClient implements ClientModInitializer {
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        StylerConfig.load();
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bauloo_styler.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.bauloo_styler"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new StylerScreen());
                }
            }
        });
    }
}
