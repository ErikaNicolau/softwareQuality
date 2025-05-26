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
        
        shapeItem.draw(g2d, 0, 0, 1.0f, null);
        
        shapeItem.setPosition(100, 100);
        shapeItem.draw(g2d, 0, 0, 1.0f, null);
        
        g2d.dispose();
    }

    @Test
    void testDifferentShapeTypes() {
        ShapeItem rectangle = new ShapeItem(1, "Rectangle", Color.BLUE);
        assertNotNull(rectangle.getBoundingBox());
        
        ShapeItem oval = new ShapeItem(1, "Oval", Color.GREEN);
        assertNotNull(oval.getBoundingBox());
        
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
        Rectangle defaultBounds = shapeItem.getBoundingBox();
        
        shapeItem.setSize(400, 300);
        Rectangle largeBounds = shapeItem.getBoundingBox();
        
        assertTrue(largeBounds.width > defaultBounds.width);
        assertTrue(largeBounds.height > defaultBounds.height);
    }

    @Test
    void testGetContent() {
        assertEquals("", shapeItem.getContent());
    }

    @Test
    void testGetLevel() {
        assertEquals(1, shapeItem.getLevel());
    }

    @Test
    void testInvalidShapeType() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ShapeItem(1, "InvalidShape", Color.BLACK)
        );
    }

    @Test
    void testNullColor() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ShapeItem(1, SHAPE_TYPE, null)
        );
    }

    @Test
    void testNegativeDimensions() {
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setSize(-100, 100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setSize(100, -100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setSize(-100, -100)
        );
    }

    @Test
    void testZeroDimensions() {
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setSize(0, 100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setSize(100, 0)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setSize(0, 0)
        );
    }

    @Test
    void testNegativePosition() {
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setPosition(-100, 100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setPosition(100, -100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.setPosition(-100, -100)
        );
    }

    @Test
    void testDrawWithNullGraphics() {
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.draw(null, 0, 0, 1.0f, null)
        );
    }

    @Test
    void testDrawWithNegativeScale() {
        BufferedImage outputImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.draw(g2d, 0, 0, -1.0f, null)
        );
        
        g2d.dispose();
    }

    @Test
    void testDrawWithZeroScale() {
        BufferedImage outputImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        
        assertThrows(IllegalArgumentException.class, () -> 
            shapeItem.draw(g2d, 0, 0, 0.0f, null)
        );
        
        g2d.dispose();
    }
} 