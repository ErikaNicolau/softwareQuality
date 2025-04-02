package com.jabberpoint.factory;

import com.jabberpoint.composite.items.ShapeItem;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class ShapeDrawer implements Drawer, ItemDrawer {
    private final ShapeItem shapeItem;

    public ShapeDrawer(ShapeItem shapeItem) {
        this.shapeItem = shapeItem;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        shapeItem.draw(g, x, y, scale, observer);
    }

    @Override
    public Rectangle getBoundingBox() {
        return shapeItem.getBoundingBox();
    }
} 