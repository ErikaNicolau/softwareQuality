package com.jabberpoint.factory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.image.ImageObserver;
import com.jabberpoint.composite.items.TextItem;

public class TextDrawer implements ItemDrawer {
    private static final int DEFAULT_FONT_SIZE = 10;
    private static final String FONT_NAME = "Helvetica";
    private static final int FONT_STYLE = Font.BOLD;
    private final TextItem textItem;

    public TextDrawer(TextItem textItem) {
        this.textItem = textItem;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        Font font = new Font(FONT_NAME, FONT_STYLE, (int) (DEFAULT_FONT_SIZE * scale));
        g.setFont(font);
        g.drawString(textItem.getContent(), x, y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return textItem.getBoundingBox();
    }
} 