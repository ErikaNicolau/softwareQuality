package com.jabberpoint.composite.items;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class BitmapItem extends SlideItem {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;
    private Image image;
    private String imageName;
    private Rectangle defaultBounds;
    private Rectangle customBounds;

    public BitmapItem(int level, String name) {
        super(level);
        this.imageName = name;
        this.defaultBounds = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File(imageName));
            if (image != null) {
                // Calculate scaled dimensions maintaining aspect ratio
                int imgWidth = image.getWidth(null);
                int imgHeight = image.getHeight(null);
                double aspectRatio = (double) imgWidth / imgHeight;
                
                int scaledWidth = DEFAULT_WIDTH;
                int scaledHeight = (int) (DEFAULT_WIDTH / aspectRatio);
                
                // Update default bounds based on scaled dimensions
                defaultBounds = new Rectangle(0, 0, scaledWidth, scaledHeight);
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + imageName);
            image = null;
            // Use default dimensions for error case
            defaultBounds = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }

    @Override
    public String getContent() {
        return imageName;
    }

    @Override
    protected Rectangle getDefaultBoundingBox() {
        return defaultBounds;
    }

    @Override
    public void setPosition(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Position coordinates cannot be negative.");
        }
        super.setPosition(x, y);
        Rectangle bounds = getBoundingBox();
        bounds.x = x;
        bounds.y = y;
    }

    @Override
    public void setSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive.");
        }
        super.setSize(width, height);
        Rectangle bounds = getBoundingBox();
        bounds.width = width;
        bounds.height = height;
    }

    @Override
    public void draw(Graphics g, int x, int y, float scale, ImageObserver observer) {
        if (image == null) {
            return;
        }

        Rectangle bounds = getBoundingBox();
        int drawX = hasCustomPosition ? bounds.x : x;
        int drawY = hasCustomPosition ? bounds.y : y;
        int drawWidth = hasCustomSize ? bounds.width : (int) (bounds.width * scale);
        int drawHeight = hasCustomSize ? bounds.height : (int) (bounds.height * scale);

        g.drawImage(image, drawX, drawY, drawWidth, drawHeight, observer);
    }
} 