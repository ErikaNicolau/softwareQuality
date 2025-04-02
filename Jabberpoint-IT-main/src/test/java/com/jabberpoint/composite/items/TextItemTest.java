package com.jabberpoint.composite.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class TextItemTest {
    private TextItem textItem;
    private Graphics2D graphics;
    private static final String TEST_TEXT = "Test Text";
    private static final int DEFAULT_FONT_SIZE = 20;

    @BeforeEach
    void setUp() {
        textItem = new TextItem(1, TEST_TEXT);
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    @Test
    void testDefaultFontSize() {
        assertEquals(DEFAULT_FONT_SIZE, textItem.getFontSize());
    }

    @Test
    void testCustomFontSize() {
        int newSize = 36;
        textItem.setFontSize(newSize);
        assertEquals(newSize, textItem.getFontSize());
    }

    @Test
    void testPosition() {
        textItem.setPosition(100, 200);
        Rectangle bounds = textItem.getBoundingBox();
        assertEquals(100, bounds.x);
        assertEquals(200, bounds.y);
    }

    @Test
    void testContent() {
        assertEquals(TEST_TEXT, textItem.getContent());
    }

    @Test
    void testDraw() {
        // Test drawing with default position
        textItem.draw(graphics, 0, 0, 1.0f, null);
        
        // Test drawing with custom position
        textItem.setPosition(100, 100);
        textItem.draw(graphics, 0, 0, 1.0f, null);
    }

    @Test
    void testBoundingBox() {
        Rectangle bounds = textItem.getBoundingBox();
        assertNotNull(bounds);
        assertTrue(bounds.width > 0);
        assertTrue(bounds.height > 0);
    }

    @Test
    void testFontSizeAffectsBoundingBox() {
        // Get bounding box with default font size
        Rectangle defaultBounds = textItem.getBoundingBox();
        
        // Increase font size
        textItem.setFontSize(48);
        Rectangle largeBounds = textItem.getBoundingBox();
        
        // Larger font should result in larger bounding box
        assertTrue(largeBounds.width >= defaultBounds.width);
        assertTrue(largeBounds.height >= defaultBounds.height);
    }

    @Test
    void testEmptyText() {
        TextItem emptyItem = new TextItem(1, "");
        Rectangle bounds = emptyItem.getBoundingBox();
        assertNotNull(bounds);
        assertTrue(bounds.width >= 0);
        assertTrue(bounds.height >= 0);
    }
} 