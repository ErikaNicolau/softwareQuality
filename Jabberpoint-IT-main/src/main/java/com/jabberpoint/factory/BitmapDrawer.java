package com.jabberpoint.factory;

import com.jabberpoint.composite.items.BitmapItem;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class BitmapDrawer implements Drawer, ItemDrawer {
    private final BitmapItem bitmapItem;

    public BitmapDrawer(BitmapItem bitmapItem) {
        this.bitmapItem = bitmapItem;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        bitmapItem.draw(g, x, y, scale, observer);
    }

    @Override
    public Rectangle getBoundingBox() {
        return bitmapItem.getBoundingBox();
    }
} 