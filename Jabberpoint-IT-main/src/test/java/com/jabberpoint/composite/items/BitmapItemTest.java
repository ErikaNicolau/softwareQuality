package com.jabberpoint.composite.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

class BitmapItemTest {
    private BitmapItem bitmapItem;
    private BufferedImage testImage;
    private Path tempImagePath;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = testImage.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 100, 100);
        g2d.dispose();

        tempImagePath = Files.createTempFile("test_image", ".png");
        javax.imageio.ImageIO.write(testImage, "PNG", tempImagePath.toFile());

        bitmapItem = new BitmapItem(1, tempImagePath.toString());
        tempDir = Files.createTempDirectory("test_dir");
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
        
        bitmapItem.draw(g2d, 0, 0, 1.0f, null);
        
        bitmapItem.setPosition(100, 100);
        bitmapItem.draw(g2d, 0, 0, 1.0f, null);
        
        g2d.dispose();
    }

    @Test
    void testAspectRatioPreservation() {
        BufferedImage wideImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        Path wideImagePath = tempImagePath.getParent().resolve("wide_test.png");
        try {
            javax.imageio.ImageIO.write(wideImage, "PNG", wideImagePath.toFile());
            BitmapItem wideItem = new BitmapItem(1, wideImagePath.toString());
            
            Rectangle bounds = wideItem.getBoundingBox();
            assertEquals(400, bounds.width);
            assertEquals(200, bounds.height);
        } catch (IOException e) {
            fail("Failed to create test image: " + e.getMessage());
        }
    }

    @Test
    void testGetContent() {
        assertEquals(tempImagePath.toString(), bitmapItem.getContent());
    }

    @Test
    void testGetLevel() {
        assertEquals(1, bitmapItem.getLevel());
    }

    @Test
    void testCorruptedImageFile() throws IOException {
        File corruptedFile = new File(tempDir.toFile(), "corrupted.png");
        try (FileWriter writer = new FileWriter(corruptedFile)) {
            writer.write("This is not a valid PNG file");
        }

        BitmapItem corruptedItem = new BitmapItem(1, corruptedFile.getAbsolutePath());
        assertNotNull(corruptedItem.getBoundingBox());
        assertEquals(400, corruptedItem.getBoundingBox().width);
        assertEquals(300, corruptedItem.getBoundingBox().height);
    }

    @Test
    void testNegativeDimensions() {
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setSize(-100, 100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setSize(100, -100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setSize(-100, -100)
        );
    }

    @Test
    void testZeroDimensions() {
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setSize(0, 100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setSize(100, 0)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setSize(0, 0)
        );
    }

    @Test
    void testNegativePosition() {
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setPosition(-100, 100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setPosition(100, -100)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            bitmapItem.setPosition(-100, -100)
        );
    }

    @Test
    void testDifferentImageFormats() throws IOException {
        // Test JPEG
        File jpegFile = new File(tempDir.toFile(), "test.jpg");
        javax.imageio.ImageIO.write(testImage, "JPEG", jpegFile);
        BitmapItem jpegItem = new BitmapItem(1, jpegFile.getAbsolutePath());
        assertNotNull(jpegItem.getBoundingBox());

        // Test GIF
        File gifFile = new File(tempDir.toFile(), "test.gif");
        javax.imageio.ImageIO.write(testImage, "GIF", gifFile);
        BitmapItem gifItem = new BitmapItem(1, gifFile.getAbsolutePath());
        assertNotNull(gifItem.getBoundingBox());

        // Test BMP
        File bmpFile = new File(tempDir.toFile(), "test.bmp");
        javax.imageio.ImageIO.write(testImage, "BMP", bmpFile);
        BitmapItem bmpItem = new BitmapItem(1, bmpFile.getAbsolutePath());
        assertNotNull(bmpItem.getBoundingBox());
    }
} 