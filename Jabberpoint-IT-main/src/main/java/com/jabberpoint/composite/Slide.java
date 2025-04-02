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

/**
 * Represents a slide in a presentation.
 * This class implements the Composite pattern as a composite component,
 * containing multiple SlideItems that can be drawn together.
 */
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

    /**
     * Creates a new empty slide.
     */
    public Slide() {
        this.title = "";
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Adds an observer to be notified of slide changes.
     * @param observer The observer to add
     */
    public void addObserver(SlideObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the slide.
     * @param observer The observer to remove
     */
    public void removeObserver(SlideObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a change in the slide.
     */
    private void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    /**
     * Adds a new item to the slide.
     * @param item The item to add
     */
    public void append(SlideItem item) {
        items.add(item);
        notifyObservers();
    }

    /**
     * Gets the title of the slide.
     * @return The current title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets a copy of all items in the slide.
     * @return A new list containing all items
     */
    public List<SlideItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Gets the item at the specified index.
     * @param index The index of the item to get
     * @return The item at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
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

    /**
     * Sets the title of the slide.
     * @param title The new title
     */
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    /**
     * Draws the slide and all its items.
     * The scale factor is calculated to fit the content within the available area
     * while maintaining aspect ratio.
     */
    public void draw(Graphics g, Rectangle area, ImageObserver observer) {
        float scale = getScale(area);
        int y = area.y;

        // Draw all items
        for (SlideItem item : items) {
            item.draw(g, area.x, y, scale, observer);
            y += item.getBoundingBox().height + 10;
        }
    }

    private float getScale(Rectangle area) {
        return Math.min((float) area.width / Constants.WIDTH, (float) area.height / Constants.HEIGHT);
    }
} 