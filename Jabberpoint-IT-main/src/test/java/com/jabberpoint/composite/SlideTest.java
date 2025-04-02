package com.jabberpoint.composite;

import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.observer.SlideObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class SlideTest {
    private Slide slide;
    private static final String SLIDE_TITLE = "Test Slide";
    private List<Boolean> observerCalled;
    private SlideObserver observer;

    @BeforeEach
    void setUp() {
        slide = new Slide();
        observerCalled = new ArrayList<>();
        observer = s -> observerCalled.add(true);
        slide.addObserver(observer);
    }

    @Test
    void testSetTitle() {
        slide.setTitle(SLIDE_TITLE);
        assertEquals(SLIDE_TITLE, slide.getTitle());
    }

    @Test
    void testAppendItem() {
        TextItem textItem = new TextItem(1, "Test Text");
        slide.append(textItem);
        assertEquals(1, slide.getItems().size());
        assertEquals(textItem, slide.getItems().get(0));
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testAppendMultipleItems() {
        TextItem textItem = new TextItem(1, "Test Text");
        ShapeItem shapeItem = new ShapeItem(1, "Rectangle", Color.RED);
        slide.append(textItem);
        slide.append(shapeItem);
        
        assertEquals(2, slide.getItems().size());
        assertEquals(textItem, slide.getItems().get(0));
        assertEquals(shapeItem, slide.getItems().get(1));
        assertEquals(2, observerCalled.size());
    }

    @Test
    void testRemoveItem() {
        TextItem textItem = new TextItem(1, "Test Text");
        slide.append(textItem);
        observerCalled.clear();
        
        slide.removeItem(0);
        assertTrue(slide.getItems().isEmpty());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testGetItem() {
        TextItem textItem = new TextItem(1, "Test Text");
        slide.append(textItem);
        assertEquals(textItem, slide.getItem(0));
    }

    @Test
    void testGetItemOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> slide.getItem(0));
    }

    @Test
    void testRemoveItemOutOfBounds() {
        slide.removeItem(0);
        assertTrue(slide.getItems().isEmpty());
    }

    @Test
    void testMultipleObservers() {
        List<Boolean> secondObserverCalled = new ArrayList<>();
        slide.addObserver(s -> secondObserverCalled.add(true));
        
        TextItem textItem = new TextItem(1, "Test Text");
        slide.append(textItem);
        
        assertTrue(observerCalled.get(0));
        assertTrue(secondObserverCalled.get(0));
    }

    @Test
    void testRemoveObserver() {
        slide.removeObserver(observer);
        TextItem textItem = new TextItem(1, "Test Text");
        slide.append(textItem);
        
        assertTrue(observerCalled.isEmpty());
    }

    @Test
    void testClearItems() {
        slide.append(new TextItem(1, "Test Text"));
        slide.append(new ShapeItem(1, "Rectangle", Color.RED));
        observerCalled.clear();
        
        slide.clearItems();
        assertTrue(slide.getItems().isEmpty());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testMixedItemTypes() {
        TextItem textItem = new TextItem(1, "Test Text");
        ShapeItem shapeItem = new ShapeItem(1, "Rectangle", Color.RED);
        BitmapItem bitmapItem = new BitmapItem(1, "test.png");
        
        slide.append(textItem);
        slide.append(shapeItem);
        slide.append(bitmapItem);
        
        assertEquals(3, slide.getItems().size());
        assertEquals(textItem, slide.getItem(0));
        assertEquals(shapeItem, slide.getItem(1));
        assertEquals(bitmapItem, slide.getItem(2));
    }

    @Test
    public void testObserverNotification() {
        Slide slide = new Slide();
        AtomicBoolean notified = new AtomicBoolean(false);
        slide.addObserver(s -> notified.set(true));
        slide.append(new TextItem(1, "Test"));
        assertTrue(notified.get());
    }
} 