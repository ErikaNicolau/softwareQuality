package com.jabberpoint.composite.items;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.ImageObserver;
import com.jabberpoint.util.Constants;

public class ShapeItem extends SlideItem {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 150;

    private String shapeType;
    private Color color;
    private Rectangle defaultBounds;

    public ShapeItem(int level, String shapeType, Color color) {
        super(level);
        this.shapeType = shapeType;
        this.color = color;
        this.defaultBounds = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // Set initial size to default
    }

    public String getShapeType() {
        return shapeType;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String getContent() {
        return shapeType + " " + color.toString();
    }

    @Override
    protected Rectangle getDefaultBoundingBox() {
        return defaultBounds;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        Rectangle bounds = getBoundingBox();
        int drawX = hasCustomPosition ? bounds.x : x;
        int drawY = hasCustomPosition ? bounds.y : y;
        int drawWidth = hasCustomSize ? bounds.width : (int) (bounds.width * scale);
        int drawHeight = hasCustomSize ? bounds.height : (int) (bounds.height * scale);

        g.setColor(color);
        switch (shapeType.toLowerCase()) {
            case "rectangle":
                g.fillRect(drawX, drawY, drawWidth, drawHeight);
                break;
            case "oval":
                g.fillOval(drawX, drawY, drawWidth, drawHeight);
                break;
            case "line":
                g.drawLine(drawX, drawY, drawX + drawWidth, drawY + drawHeight);
                break;
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }
} 