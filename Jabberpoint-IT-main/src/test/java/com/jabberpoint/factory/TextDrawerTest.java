package com.jabberpoint.factory;

import com.jabberpoint.composite.items.TextItem;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TextDrawerTest {

    @Test
    void draw_shouldSetFontAndDrawString() {
        // Arrange
        TextItem mockTextItem = mock(TextItem.class);
        Graphics mockGraphics = mock(Graphics.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        TextDrawer drawer = new TextDrawer(mockTextItem);

        String textContent = "Test Text";
        when(mockTextItem.getContent()).thenReturn(textContent);

        int x = 100;
        int y = 150;
        float scale = 2.0f;

        // Act
        drawer.draw(mockGraphics, x, y, scale, mockObserver);

        // Assert
        // Verify setFont was called with the expected font details
        verify(mockGraphics, times(1)).setFont(any(Font.class));
        // We can't easily assert the exact Font object due to its creation inside draw,
        // but we can verify drawString was called with the correct text and coordinates.
        verify(mockGraphics, times(1)).drawString(textContent, x, y);
    }

    @Test
    void getBoundingBox_shouldReturnBoundingBoxFromTextItem() {
        // Arrange
        TextItem mockTextItem = mock(TextItem.class);
        Rectangle expectedBounds = new Rectangle(10, 20, 30, 40);
        when(mockTextItem.getBoundingBox()).thenReturn(expectedBounds);
        TextDrawer drawer = new TextDrawer(mockTextItem);

        // Act
        Rectangle actualBounds = drawer.getBoundingBox();

        // Assert
        assertEquals(expectedBounds, actualBounds);
        // Verify that getBoundingBox was called on the underlying TextItem
        verify(mockTextItem, times(1)).getBoundingBox();
    }
} 