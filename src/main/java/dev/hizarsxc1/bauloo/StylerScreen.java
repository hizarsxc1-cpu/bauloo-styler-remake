package dev.hizarsxc1.bauloo;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class StylerScreen extends Screen {
    private int page = 0;

    public StylerScreen() {
        super(Text.literal("Bauloo Styler Remake"));
    }

    @Override
    protected void init() {
        String[] categories = StylerConfig.categories();
        int columns = this.width >= 520 ? 2 : 1;
        int pageSize = columns == 2 ? 8 : 4;
        int pageCount = (categories.length + pageSize - 1) / pageSize;
        page = Math.max(0, Math.min(page, pageCount - 1));

        int outer = 12;
        int gap = 8;
        int groupWidth = Math.max(120, (this.width - outer * 2 - gap * (columns - 1)) / columns);
        int startX = (this.width - (groupWidth * columns + gap * (columns - 1))) / 2;
        int startY = 70;
        int rowHeight = 38;
        int first = page * pageSize;
        int last = Math.min(categories.length, first + pageSize);

        for (int i = first; i < last; i++) {
            final String category = categories[i];
            int local = i - first;
            int column = local % columns;
            int row = local / columns;
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

        int rows = (last - first + columns - 1) / columns;
        int navY = Math.min(this.height - 28, startY + rows * rowHeight + 4);
        int navWidth = 76;
        addDrawableChild(ButtonWidget.builder(Text.literal("Export Glowstone Pack"), button -> {
            try {
                java.nio.file.Path pack = StylePackExporter.exportGlowstone(StylerConfig.styleIndex("Glowstone"));
                if (client != null && client.player != null) {
                    client.player.sendMessage(Text.literal("Exported: " + pack.getFileName() + ". Enable it in Options > Resource Packs."), false);
                }
            } catch (java.io.IOException exception) {
                if (client != null && client.player != null) {
                    client.player.sendMessage(Text.literal("Could not export Glowstone pack: " + exception.getMessage()), false);
                }
            }
        }).dimensions(this.width / 2 - 82, this.height - 54, 164, 20).build());

        if (page > 0) {
            addDrawableChild(ButtonWidget.builder(Text.literal("< Back"), button -> {
                page--;
                clearAndInit();
            }).dimensions(this.width / 2 - navWidth - 4, navY, navWidth, 20).build());
        }
        if (page < pageCount - 1) {
            addDrawableChild(ButtonWidget.builder(Text.literal("More >"), button -> {
                page++;
                clearAndInit();
            }).dimensions(this.width / 2 + 4, navY, navWidth, 20).build());
        } else {
            addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> close())
                    .dimensions(this.width / 2 - 65, navY, 130, 20).build());
        }
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
                Text.literal("Style library  |  Page " + (page + 1)),
                this.width / 2, 39, 0xA0A0A0);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Tap left to toggle, right to change style"),
                this.width / 2, 52, 0x808080);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
