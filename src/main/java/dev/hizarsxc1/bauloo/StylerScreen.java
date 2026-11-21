package dev.hizarsxc1.bauloo;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class StylerScreen extends Screen {
    private String selectedCategory = "Crystal";

    public StylerScreen() {
        super(Text.literal("Bauloo Styler Remake"));
    }

    @Override
    protected void init() {
        clearChildren();
        String[] categories = StylerConfig.categories();

        int sidebarX = 10;
        int sidebarWidth = Math.max(88, Math.min(112, this.width / 4));
        int contentX = sidebarX + sidebarWidth + 12;
        int contentWidth = Math.max(120, this.width - contentX - 10);
        int top = 46;

        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];
            int y = top + i * 19;
            if (y + 18 > this.height - 32) break;
            boolean active = category.equals(selectedCategory);
            addDrawableChild(ButtonWidget.builder(
                    Text.literal((active ? "> " : "") + category),
                    button -> {
                        selectedCategory = category;
                        clearAndInit();
                    }).dimensions(sidebarX, y, sidebarWidth, 18).build());
        }

        addDrawableChild(ButtonWidget.builder(
                Text.literal(StylerConfig.isEnabled(selectedCategory) ? "Enabled" : "Disabled"),
                button -> {
                    StylerConfig.toggle(selectedCategory);
                    button.setMessage(Text.literal(StylerConfig.isEnabled(selectedCategory) ? "Enabled" : "Disabled"));
                }).dimensions(contentX + contentWidth - 86, 42, 86, 20).build());

        String[] styles = StylerConfig.styles(selectedCategory);
        int columns = contentWidth >= 430 ? 2 : 2;
        int gap = 7;
        int cardWidth = Math.max(52, (contentWidth - gap) / columns);
        int cardY = 157;
        int cardHeight = 38;

        for (int i = 0; i < styles.length; i++) {
            final int styleIndex = i;
            int col = i % columns;
            int row = i / columns;
            int x = contentX + col * (cardWidth + gap);
            int y = cardY + row * (cardHeight + 7);
            if (y + cardHeight > this.height - 34) break;
            boolean chosen = StylerConfig.styleIndex(selectedCategory) == i;
            String label = (chosen ? "✓ " : "") + styles[i];
            addDrawableChild(ButtonWidget.builder(Text.literal(label), button -> {
                StylerConfig.selectStyle(selectedCategory, styleIndex);
                clearAndInit();
            }).dimensions(x, y, cardWidth, cardHeight).build());
        }

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
        }).dimensions(10, this.height - 26, 150, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> close())
                .dimensions(this.width - 82, this.height - 26, 72, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 13, 0xFFFFFF);
        context.drawText(this.textRenderer, Text.literal("CATEGORIES"), 10, 31, 0xA0A0A0, false);

        int sidebarWidth = Math.max(88, Math.min(112, this.width / 4));
        int contentX = 10 + sidebarWidth + 12;
        int contentWidth = Math.max(120, this.width - contentX - 10);

        context.fill(contentX - 4, 31, this.width - 8, this.height - 32, 0x66303030);
        context.drawText(this.textRenderer, Text.literal(selectedCategory + " styles"), contentX + 4, 32, 0xFFFFFF, false);
        context.drawText(this.textRenderer, Text.literal("Choose a card to apply its style"), contentX + 4, 55, 0xA0A0A0, false);

        int selected = StylerConfig.styleIndex(selectedCategory);
        String style = StylerConfig.styleName(selectedCategory);
        int previewTop = 82;
        context.fill(contentX + 4, previewTop, contentX + contentWidth - 4, 145, 0xAA171B25);
        context.fill(contentX + 12, previewTop + 10, contentX + 62, previewTop + 53, previewColor(selectedCategory, selected));
        context.fill(contentX + 18, previewTop + 16, contentX + 56, previewTop + 47, 0xFF252A38);
        context.drawText(this.textRenderer, Text.literal("PREVIEW"), contentX + 72, previewTop + 9, 0xFFB9C8E8, false);
        context.drawText(this.textRenderer, Text.literal(style), contentX + 72, previewTop + 25, 0xFFFFFFFF, false);
        context.drawText(this.textRenderer, Text.literal("Style " + (selected + 1) + " / " + StylerConfig.styles(selectedCategory).length),
                contentX + 72, previewTop + 40, 0xFFAAAAAA, false);

        super.render(context, mouseX, mouseY, delta);
    }

    private int previewColor(String category, int style) {
        int[][] palettes = {
                {0xFF6D75A8, 0xFF38C6D9, 0xFF9867D5, 0xFF333744},
                {0xFF8B6B48, 0xFF3A9BD9, 0xFF8D55D9, 0xFFB8D9E8},
                {0xFF8C8C8C, 0xFF36B76B, 0xFFE88DB8, 0xFF34343D},
                {0xFF777777, 0xFFE5C84B, 0xFF8BC8E8, 0xFFE07845}
        };
        int categoryHash = Math.abs(category.hashCode());
        return palettes[categoryHash % palettes.length][Math.floorMod(style, 4)];
    }

    @Override
    public boolean shouldPause() { return false; }
}
