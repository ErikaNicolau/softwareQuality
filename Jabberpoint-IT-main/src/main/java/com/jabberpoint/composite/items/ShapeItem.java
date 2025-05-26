package com.jabberpoint.composite.items;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.ImageObserver;
import com.jabberpoint.util.Constants;

public class ShapeItem extends SlideItem {
    private String shapeType;
    private Color color;
    private Rectangle defaultBounds;

    public ShapeItem(int level, String shapeType, Color color) {
        super(level);
        if (shapeType == null || shapeType.trim().isEmpty()) {
            throw new IllegalArgumentException("Shape type cannot be null or empty.");
        }
        // Validate shape type against supported types
        String lowerShapeType = shapeType.toLowerCase();
        if (!lowerShapeType.equals("rectangle") && !lowerShapeType.equals("oval") && !lowerShapeType.equals("line")) {
             throw new IllegalArgumentException("Invalid shape type: " + shapeType + ". Supported types are Rectangle, Oval, Line.");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }
        this.shapeType = shapeType;
        this.color = color;
        this.defaultBounds = new Rectangle(0, 0, Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        setSize(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT); // Set initial size to default
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
        if (g == null) {
             throw new IllegalArgumentException("Graphics object cannot be null.");
        }
        if (scale <= 0) {
             throw new IllegalArgumentException("Scale must be positive.");
        }
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

    @Override
    public void setPosition(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Position coordinates cannot be negative.");
        }
        super.setPosition(x, y);
    }

    @Override
    public void setSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive.");
        }
        super.setSize(width, height);
    }
} 