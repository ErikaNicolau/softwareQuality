package com.jabberpoint.factory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.image.ImageObserver;
import com.jabberpoint.composite.items.TextItem;

public class TextDrawer implements ItemDrawer {
    private final TextItem textItem;

    public TextDrawer(TextItem textItem) {
        this.textItem = textItem;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        Font font = new Font("Helvetica", Font.BOLD, (int) (textItem.getFontSize() * scale));
        g.setFont(font);
        g.drawString(textItem.getContent(), x, y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return textItem.getBoundingBox();
    }
} 