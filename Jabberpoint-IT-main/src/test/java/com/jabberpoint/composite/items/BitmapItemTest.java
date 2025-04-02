package com.jabberpoint.composite.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class BitmapItemTest {
    private BitmapItem bitmapItem;
    private BufferedImage testImage;
    private Path tempImagePath;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary test image
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = testImage.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 100, 100);
        g2d.dispose();

        // Save the test image to a temporary file
        tempImagePath = Files.createTempFile("test_image", ".png");
        javax.imageio.ImageIO.write(testImage, "PNG", tempImagePath.toFile());

        bitmapItem = new BitmapItem(1, tempImagePath.toString());
    }

    @Test
    void testDefaultSize() {
        assertEquals(400, bitmapItem.getBoundingBox().width);
        assertEquals(400, bitmapItem.getBoundingBox().height);
    }

    @Test
    void testCustomSize() {
        bitmapItem.setSize(200, 150);
        assertEquals(200, bitmapItem.getBoundingBox().width);
        assertEquals(150, bitmapItem.getBoundingBox().height);
    }

    @Test
    void testPosition() {
        bitmapItem.setPosition(100, 200);
        assertEquals(100, bitmapItem.getBoundingBox().x);
        assertEquals(200, bitmapItem.getBoundingBox().y);
    }

    @Test
    void testInvalidImagePath() {
        BitmapItem invalidItem = new BitmapItem(1, "nonexistent.png");
        assertNotNull(invalidItem.getBoundingBox());
        assertEquals(400, invalidItem.getBoundingBox().width);
        assertEquals(300, invalidItem.getBoundingBox().height);
    }

    @Test
    void testDraw() {
        BufferedImage outputImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        
        // Test drawing with default position
        bitmapItem.draw(g2d, 0, 0, 1.0f, null);
        
        // Test drawing with custom position
        bitmapItem.setPosition(100, 100);
        bitmapItem.draw(g2d, 0, 0, 1.0f, null);
        
        g2d.dispose();
    }

    @Test
    void testAspectRatioPreservation() {
        // Create a 2:1 aspect ratio image
        BufferedImage wideImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        Path wideImagePath = tempImagePath.getParent().resolve("wide_test.png");
        try {
            javax.imageio.ImageIO.write(wideImage, "PNG", wideImagePath.toFile());
            BitmapItem wideItem = new BitmapItem(1, wideImagePath.toString());
            
            // Check if the height is half the width
            Rectangle bounds = wideItem.getBoundingBox();
            assertEquals(400, bounds.width);
            assertEquals(200, bounds.height);
        } catch (IOException e) {
            fail("Failed to create test image: " + e.getMessage());
        }
    }
} 