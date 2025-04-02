package com.jabberpoint.view;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.observer.SlideObserver;
import com.jabberpoint.factory.DrawerFactory;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;

public class SlideViewerComponent extends JComponent implements SlideObserver {
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;
    private static final int TITLE_FONT_SIZE = 36;
    private static final int ITEM_SPACING = 30;
    private static final String FONT_NAME = "Segoe UI";
    private static final int FONT_STYLE = Font.BOLD;
    private static final Color BACKGROUND_COLOR_TOP = new Color(240, 240, 255);
    private static final Color BACKGROUND_COLOR_BOTTOM = new Color(220, 220, 240);
    private static final Color TEXT_COLOR = new Color(40, 40, 40);
    private static final int CONTENT_MARGIN = 50;
    private static final float TITLE_OPACITY = 0.9f;

    private final Presentation presentation;
    private final JFrame frame;
    private final DrawerFactory drawerFactory;

    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
        this.drawerFactory = new DrawerFactory();
        
        // Register as an observer for the presentation
        presentation.addObserver(this);
        
        setBackground(BACKGROUND_COLOR_TOP);
        setFocusable(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void update(Slide slide) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw gradient background
        GradientPaint gradient = new GradientPaint(
            0, 0, BACKGROUND_COLOR_TOP,
            0, getHeight(), BACKGROUND_COLOR_BOTTOM
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide != null) {
            // Set up the content area with margins
            Rectangle area = new Rectangle(
                CONTENT_MARGIN, 
                CONTENT_MARGIN, 
                getWidth() - (2 * CONTENT_MARGIN), 
                getHeight() - (2 * CONTENT_MARGIN)
            );

            // Draw a subtle shadow effect
            g2d.setColor(new Color(0, 0, 0, 20));
            g2d.fillRoundRect(
                area.x + 5, area.y + 5, 
                area.width, area.height, 
                20, 20
            );

            // Draw the content area
            g2d.setColor(new Color(255, 255, 255, 240));
            g2d.fillRoundRect(
                area.x, area.y, 
                area.width, area.height, 
                20, 20
            );

            // Draw the slide content
            currentSlide.draw(g2d, area, this);
        }
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if ((infoflags & ImageObserver.ALLBITS) != 0) {
            repaint();
            return false;
        }
        return true;
    }
} 