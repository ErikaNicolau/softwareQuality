package com.jabberpoint.factory;

import com.jabberpoint.composite.items.ShapeItem;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShapeDrawerTest {

    @Test
    void draw_shouldCallDrawOnShapeItem() {
        ShapeItem mockShapeItem = mock(ShapeItem.class);
        Graphics mockGraphics = mock(Graphics.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        ShapeDrawer drawer = new ShapeDrawer(mockShapeItem);

        int x = 100;
        int y = 200;
        float scale = 0.5f;

        drawer.draw(mockGraphics, x, y, scale, mockObserver);

        verify(mockShapeItem, times(1)).draw(mockGraphics, x, y, scale, mockObserver);
    }

    @Test
    void getBoundingBox_shouldReturnBoundingBoxFromShapeItem() {
        ShapeItem mockShapeItem = mock(ShapeItem.class);
        Rectangle expectedBounds = new Rectangle(20, 30, 150, 80);
        when(mockShapeItem.getBoundingBox()).thenReturn(expectedBounds);
        ShapeDrawer drawer = new ShapeDrawer(mockShapeItem);

        Rectangle actualBounds = drawer.getBoundingBox();

        assertEquals(expectedBounds, actualBounds);
        verify(mockShapeItem, times(1)).getBoundingBox();
    }
} 