package dev.hizarsxc1.bauloo;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class StylerScreen extends Screen {
    public StylerScreen() {
        super(Text.literal("Bauloo Styler Remake"));
    }

    @Override
    protected void init() {
        String[] categories = StylerConfig.categories();
        int buttonWidth = Math.min(190, (this.width - 36) / 2);
        int gap = 8;
        int totalWidth = buttonWidth * 2 + gap;
        int startX = (this.width - totalWidth) / 2;
        int startY = Math.max(48, this.height / 2 - 78);

        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];
            int column = i % 2;
            int row = i / 2;
            int x = startX + column * (buttonWidth + gap);
            int y = startY + row * 32;
            addDrawableChild(ButtonWidget.builder(buttonText(category), button -> {
                StylerConfig.toggle(category);
                button.setMessage(buttonText(category));
            }).dimensions(x, y, buttonWidth, 24).build());
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> close())
                .dimensions(this.width / 2 - 75, Math.min(this.height - 34, startY + 4 * 32 + 8), 150, 20)
                .build());
    }

    private Text buttonText(String category) {
        return Text.literal(category + ": " + (StylerConfig.isEnabled(category) ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 22, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Independent style controls - press G to open"),
                this.width / 2, 36, 0xA0A0A0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
