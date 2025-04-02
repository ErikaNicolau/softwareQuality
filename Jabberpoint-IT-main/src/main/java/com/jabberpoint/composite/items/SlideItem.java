package com.jabberpoint.composite.items;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Dimension;

public abstract class SlideItem {
    protected int level = 0;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean hasCustomPosition;
    protected boolean hasCustomSize;

    public SlideItem(int level) {
        this.level = level;
        this.hasCustomPosition = false;
        this.hasCustomSize = false;
    }

    public int getLevel() {
        return level;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasCustomPosition = true;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.hasCustomSize = true;
    }

    public Rectangle getBoundingBox() {
        if (hasCustomPosition && hasCustomSize) {
            return new Rectangle(x, y, width, height);
        }
        return getDefaultBoundingBox();
    }

    protected abstract Rectangle getDefaultBoundingBox();

    public abstract void draw(Graphics g, int x, int y, float scale, ImageObserver observer);

    public abstract String getContent();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
} 