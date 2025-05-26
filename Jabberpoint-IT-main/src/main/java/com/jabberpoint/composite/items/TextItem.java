package com.jabberpoint.composite.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import com.jabberpoint.util.Constants;

public class TextItem extends SlideItem {
    private String text;
    private int fontSize;

    public TextItem() {
        this(Constants.DEFAULT_LEVEL, Constants.DEFAULT_TEXT);
    }

    public TextItem(int level, String text) {
        super(level);
        this.text = text;
        this.fontSize = Constants.DEFAULT_FONT_SIZE;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    @Override
    public String getContent() {
        return text;
    }

    @Override
    protected Rectangle getDefaultBoundingBox() {
        return new Rectangle(0, 0, 0, 0); // Will be calculated in draw method
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        Font font = new Font(Constants.FONT_NAME, Constants.FONT_STYLE, (int) (fontSize * scale));
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        
        Rectangle bounds = getBoundingBox();
        int drawX = hasCustomPosition ? bounds.x : x;
        int drawY = hasCustomPosition ? bounds.y : y;
        
        g.setColor(Color.black);
        g.drawString(text, drawX, drawY + metrics.getAscent());
    }

    @Override
    public Rectangle getBoundingBox() {
        // Estimate bounds based on font size and text length
        int estimatedWidth = (int) (text.length() * fontSize * 0.6); // Rough estimate
        int estimatedHeight = fontSize;
        return new Rectangle(x, y, estimatedWidth, estimatedHeight);
    }
} 