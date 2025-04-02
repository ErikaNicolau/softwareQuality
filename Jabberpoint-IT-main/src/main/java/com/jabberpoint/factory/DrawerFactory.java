package com.jabberpoint.factory;

import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.ShapeItem;

public class DrawerFactory {
    public static ItemDrawer createDrawer(SlideItem item) {
        if (item instanceof BitmapItem bitmapItem) {
            return new BitmapDrawer(bitmapItem);
        } else if (item instanceof TextItem textItem) {
            return new TextDrawer(textItem);
        } else if (item instanceof ShapeItem shapeItem) {
            return new ShapeDrawer(shapeItem);
        }
        throw new IllegalArgumentException("Unknown item type: " + item.getClass().getName());
    }
} 