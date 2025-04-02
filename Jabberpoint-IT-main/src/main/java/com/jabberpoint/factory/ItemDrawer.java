package com.jabberpoint.factory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public interface ItemDrawer {
    void draw(Graphics g, int x, int y, float scale, ImageObserver observer);
    Rectangle getBoundingBox();
} 