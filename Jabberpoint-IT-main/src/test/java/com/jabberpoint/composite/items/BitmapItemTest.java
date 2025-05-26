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
import com.jabberpoint.util.Constants;

class BitmapItemTest {
    private BitmapItem bitmapItem;
    private BufferedImage testImage100x100;
    private BufferedImage testImage200x100;
    private Path tempImagePath100x100;
    private Path tempImagePath200x100;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        // Create a 100x100 test image
        testImage100x100 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d100x100 = testImage100x100.createGraphics();
        g2d100x100.setColor(Color.RED);
        g2d100x100.fillRect(0, 0, 100, 100);
        g2d100x100.dispose();

        tempImagePath100x100 = Files.createTempFile("test_image_100x100", ".png");
        javax.imageio.ImageIO.write(testImage100x100, "PNG", tempImagePath100x100.toFile());

        // Create a 200x100 test image
        testImage200x100 = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d200x100 = testImage200x100.createGraphics();
        g2d200x100.setColor(Color.BLUE);
        g2d200x100.fillRect(0, 0, 200, 100);
        g2d200x100.dispose();

        tempImagePath200x100 = Files.createTempFile("test_image_200x100", ".png");
        javax.imageio.ImageIO.write(testImage200x100, "PNG", tempImagePath200x100.toFile());

        bitmapItem = new BitmapItem(1, tempImagePath100x100.toString());
        tempDir = Files.createTempDirectory("test_dir");
    }

    @Test
    void testDefaultSizeWith100x100Image() {
        // Default size is based on Constants.DEFAULT_WIDTH, maintaining aspect ratio
        assertEquals(Constants.DEFAULT_WIDTH, bitmapItem.getBoundingBox().width);
        assertEquals(Constants.DEFAULT_WIDTH, bitmapItem.getBoundingBox().height);
    }

    @Test
    void testDefaultSizeWith200x100Image() {
        BitmapItem wideItem = new BitmapItem(1, tempImagePath200x100.toString());
        // Default size is based on Constants.DEFAULT_WIDTH, maintaining aspect ratio
        assertEquals(Constants.DEFAULT_WIDTH, wideItem.getBoundingBox().width);
        assertEquals(Constants.DEFAULT_WIDTH / 2, wideItem.getBoundingBox().height);
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
        // Should fall back to default dimensions from Constants
        assertEquals(Constants.DEFAULT_WIDTH, invalidItem.getBoundingBox().width);
        assertEquals(Constants.DEFAULT_HEIGHT, invalidItem.getBoundingBox().height);
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
    void testAspectRatioPreservationWhenSettingSizeAfterLoading() {
        BitmapItem item = new BitmapItem(1, tempImagePath200x100.toString());
        // After loading, default bounds are set based on aspect ratio and default width.
        // Setting size should override this.
        item.setSize(300, 200);
        Rectangle bounds = item.getBoundingBox();
        assertEquals(300, bounds.width);
        assertEquals(200, bounds.height);
    }

    @Test
    void testGetContent() {
        assertEquals(tempImagePath100x100.toString(), bitmapItem.getContent());
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
        // Should fall back to default dimensions from Constants
        assertEquals(Constants.DEFAULT_WIDTH, corruptedItem.getBoundingBox().width);
        assertEquals(Constants.DEFAULT_HEIGHT, corruptedItem.getBoundingBox().height);
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
        javax.imageio.ImageIO.write(testImage100x100, "JPEG", jpegFile);
        BitmapItem jpegItem = new BitmapItem(1, jpegFile.getAbsolutePath());
        assertNotNull(jpegItem.getBoundingBox());

        // Test GIF
        File gifFile = new File(tempDir.toFile(), "test.gif");
        javax.imageio.ImageIO.write(testImage100x100, "GIF", gifFile);
        BitmapItem gifItem = new BitmapItem(1, gifFile.getAbsolutePath());
        assertNotNull(gifItem.getBoundingBox());

        // Test BMP
        File bmpFile = new File(tempDir.toFile(), "test.bmp");
        javax.imageio.ImageIO.write(testImage100x100, "BMP", bmpFile);
        BitmapItem bmpItem = new BitmapItem(1, bmpFile.getAbsolutePath());
        assertNotNull(bmpItem.getBoundingBox());
    }
} 