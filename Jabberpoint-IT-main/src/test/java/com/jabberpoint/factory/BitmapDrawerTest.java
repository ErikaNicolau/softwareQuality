package com.jabberpoint.factory;

import com.jabberpoint.composite.items.BitmapItem;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BitmapDrawerTest {

    @Test
    void draw_shouldCallDrawOnBitmapItem() {
        // Arrange
        BitmapItem mockBitmapItem = mock(BitmapItem.class);
        Graphics mockGraphics = mock(Graphics.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        BitmapDrawer drawer = new BitmapDrawer(mockBitmapItem);

        int x = 50;
        int y = 75;
        float scale = 1.5f;

        // Act
        drawer.draw(mockGraphics, x, y, scale, mockObserver);

        // Assert
        verify(mockBitmapItem, times(1)).draw(mockGraphics, x, y, scale, mockObserver);
    }

    @Test
    void getBoundingBox_shouldReturnBoundingBoxFromBitmapItem() {
        // Arrange
        BitmapItem mockBitmapItem = mock(BitmapItem.class);
        Rectangle expectedBounds = new Rectangle(10, 20, 100, 50);
        when(mockBitmapItem.getBoundingBox()).thenReturn(expectedBounds);
        BitmapDrawer drawer = new BitmapDrawer(mockBitmapItem);

        // Act
        Rectangle actualBounds = drawer.getBoundingBox();

        // Assert
        assertEquals(expectedBounds, actualBounds);
        verify(mockBitmapItem, times(1)).getBoundingBox();
    }
} 