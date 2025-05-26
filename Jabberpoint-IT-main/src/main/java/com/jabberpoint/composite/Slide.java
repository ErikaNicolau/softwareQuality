package com.jabberpoint.composite;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.observer.SlideObserver;
import com.jabberpoint.util.Constants;

public class Slide {
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;
    private static final int TITLE_FONT_SIZE = 20;
    private static final int ITEM_SPACING = 20;
    private static final String FONT_NAME = "Helvetica";
    private static final int FONT_STYLE = Font.BOLD;

    private String title;
    private final List<SlideItem> items;
    private final List<SlideObserver> observers;

    public Slide() {
        this.title = "";
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addObserver(SlideObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SlideObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    public void append(SlideItem item) {
        items.add(item);
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public List<SlideItem> getItems() {
        return new ArrayList<>(items);
    }

    public SlideItem getItem(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + items.size());
        }
        return items.get(index);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            notifyObservers();
        }
    }

    public void clearItems() {
        items.clear();
        notifyObservers();
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public void draw(Graphics g, Rectangle area, ImageObserver observer) {
        float scale = getScale(area);
        int y = area.y;

        // Draw all items
        for (SlideItem item : items) {
            item.draw(g, area.x, y, scale, observer);
            y += item.getBoundingBox().height + Constants.ITEM_SPACING;
        }
    }

    private float getScale(Rectangle area) {
        return Math.min((float) area.width / Constants.WIDTH, (float) area.height / Constants.HEIGHT);
    }
} 