package com.jabberpoint.composite.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ShapeItemTest {
    private ShapeItem shapeItem;
    private static final String SHAPE_TYPE = "Rectangle";
    private static final Color SHAPE_COLOR = Color.RED;
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 150;

    @BeforeEach
    void setUp() {
        shapeItem = new ShapeItem(1, SHAPE_TYPE, SHAPE_COLOR);
    }

    @Test
    void testDefaultSize() {
        assertEquals(DEFAULT_WIDTH, shapeItem.getBoundingBox().width);
        assertEquals(DEFAULT_HEIGHT, shapeItem.getBoundingBox().height);
    }

    @Test
    void testCustomSize() {
        int newWidth = 300;
        int newHeight = 200;
        shapeItem.setSize(newWidth, newHeight);
        assertEquals(newWidth, shapeItem.getBoundingBox().width);
        assertEquals(newHeight, shapeItem.getBoundingBox().height);
    }

    @Test
    void testPosition() {
        shapeItem.setPosition(100, 200);
        assertEquals(100, shapeItem.getBoundingBox().x);
        assertEquals(200, shapeItem.getBoundingBox().y);
    }

    @Test
    void testShapeType() {
        assertEquals(SHAPE_TYPE, shapeItem.getShapeType());
    }

    @Test
    void testColor() {
        assertEquals(SHAPE_COLOR, shapeItem.getColor());
    }

    @Test
    void testDraw() {
        BufferedImage outputImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        
        // Test drawing with default position
        shapeItem.draw(g2d, 0, 0, 1.0f, null);
        
        // Test drawing with custom position
        shapeItem.setPosition(100, 100);
        shapeItem.draw(g2d, 0, 0, 1.0f, null);
        
        g2d.dispose();
    }

    @Test
    void testDifferentShapeTypes() {
        // Test Rectangle
        ShapeItem rectangle = new ShapeItem(1, "Rectangle", Color.BLUE);
        assertNotNull(rectangle.getBoundingBox());
        
        // Test Oval
        ShapeItem oval = new ShapeItem(1, "Oval", Color.GREEN);
        assertNotNull(oval.getBoundingBox());
        
        // Test Line
        ShapeItem line = new ShapeItem(1, "Line", Color.BLACK);
        assertNotNull(line.getBoundingBox());
    }

    @Test
    void testBoundingBox() {
        Rectangle bounds = shapeItem.getBoundingBox();
        assertNotNull(bounds);
        assertTrue(bounds.width > 0);
        assertTrue(bounds.height > 0);
    }

    @Test
    void testSizeAffectsBoundingBox() {
        // Get bounding box with default size
        Rectangle defaultBounds = shapeItem.getBoundingBox();
        
        // Increase size
        shapeItem.setSize(400, 300);
        Rectangle largeBounds = shapeItem.getBoundingBox();
        
        // Larger size should result in larger bounding box
        assertTrue(largeBounds.width > defaultBounds.width);
        assertTrue(largeBounds.height > defaultBounds.height);
    }
} 