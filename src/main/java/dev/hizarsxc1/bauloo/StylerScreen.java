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
        int columns = this.width >= 520 ? 2 : 1;
        int outer = 12;
        int gap = 8;
        int groupWidth = Math.max(120, (this.width - outer * 2 - gap * (columns - 1)) / columns);
        int startX = (this.width - (groupWidth * columns + gap * (columns - 1))) / 2;
        int startY = 62;
        int rowHeight = 38;

        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];
            int column = i % columns;
            int row = i / columns;
            int x = startX + column * (groupWidth + gap);
            int y = startY + row * rowHeight;
            int styleWidth = Math.max(72, groupWidth * 43 / 100);
            int toggleWidth = groupWidth - styleWidth - 4;

            addDrawableChild(ButtonWidget.builder(toggleText(category), button -> {
                StylerConfig.toggle(category);
                button.setMessage(toggleText(category));
            }).dimensions(x, y, toggleWidth, 26).build());

            addDrawableChild(ButtonWidget.builder(styleText(category), button -> {
                StylerConfig.cycleStyle(category);
                button.setMessage(styleText(category));
            }).dimensions(x + toggleWidth + 4, y, styleWidth, 26).build());
        }

        int rows = (categories.length + columns - 1) / columns;
        int doneY = Math.min(this.height - 30, startY + rows * rowHeight + 6);
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> close())
                .dimensions(this.width / 2 - 65, doneY, 130, 20).build());
    }

    private Text toggleText(String category) {
        return Text.literal(category + " " + (StylerConfig.isEnabled(category) ? "ON" : "OFF"));
    }

    private Text styleText(String category) {
        return Text.literal("< " + StylerConfig.styleName(category) + " >");
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 22, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Toggle a category or tap its style selector"),
                this.width / 2, 39, 0xA0A0A0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
