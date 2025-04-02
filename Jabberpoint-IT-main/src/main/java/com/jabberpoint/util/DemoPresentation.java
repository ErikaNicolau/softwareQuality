package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.ShapeItem;
import java.awt.Color;

/**
 * Utility class for creating a demo presentation.
 */
public class DemoPresentation {
    /**
     * Creates a demo presentation with sample slides.
     * @return A presentation with demo content
     */
    public static Presentation createDemoPresentation() {
        Presentation presentation = new Presentation();
        presentation.setTitle("Welcome to JabberPoint");

        // First slide - Welcome
        Slide slide1 = new Slide();
        slide1.setTitle("Welcome");
        
        // Add decorative background shape with a nicer color
        ShapeItem bgShape1 = new ShapeItem(0, "rectangle", new Color(65, 105, 225, 20));
        bgShape1.setSize(900, 600);
        bgShape1.setPosition(50, 50);
        slide1.append(bgShape1);
        
        // Add welcome text with better positioning and larger size
        TextItem welcomeText = new TextItem(0, "Welcome to");
        welcomeText.setFontSize(56);
        welcomeText.setPosition(150, 180);
        slide1.append(welcomeText);
        
        // Add main title with larger size and better positioning
        TextItem mainTitle = new TextItem(0, "JabberPoint");
        mainTitle.setFontSize(92);
        mainTitle.setPosition(150, 270);
        slide1.append(mainTitle);
        
        // Add subtitle with better spacing
        TextItem subtitle = new TextItem(1, "The Modern Java Presentation Tool");
        subtitle.setFontSize(36);
        subtitle.setPosition(150, 350);
        slide1.append(subtitle);
        
        // Add version info with better styling
        TextItem version = new TextItem(2, "Version 2.0");
        version.setFontSize(24);
        version.setPosition(150, 420);
        slide1.append(version);
        
        // Add credits with better layout and personalized text
        TextItem credits1 = new TextItem(2, "Originally created by Ian Darwin (1996-2000)");
        credits1.setFontSize(20);
        credits1.setPosition(150, 500);
        slide1.append(credits1);
        
        TextItem credits2 = new TextItem(2, "Enhanced by Mihaela and Erika, Pro IT Specialists");
        credits2.setFontSize(20);
        credits2.setPosition(150, 530);
        slide1.append(credits2);
        
        presentation.append(slide1);

        // Second slide - Features
        Slide slide2 = new Slide();
        slide2.setTitle("Features");
        
        // Add decorative background shape with a nicer color
        ShapeItem bgShape2 = new ShapeItem(0, "rectangle", new Color(65, 105, 225, 15));
        bgShape2.setSize(900, 600);
        bgShape2.setPosition(50, 50);
        slide2.append(bgShape2);
        
        // Add main heading with better styling
        TextItem featureTitle = new TextItem(0, "Powerful Features");
        featureTitle.setFontSize(72);
        featureTitle.setPosition(150, 150);
        slide2.append(featureTitle);
        
        // Add subheading
        TextItem featureSubtitle = new TextItem(1, "Everything you need for great presentations");
        featureSubtitle.setFontSize(32);
        featureSubtitle.setPosition(150, 230);
        slide2.append(featureSubtitle);
        
        // Add features in two columns with better spacing and styling
        TextItem feature1 = new TextItem(1, "✦  Modern Interface");
        feature1.setFontSize(32);
        feature1.setPosition(200, 320);
        slide2.append(feature1);
        
        TextItem feature2 = new TextItem(1, "✦  Rich Text Support");
        feature2.setFontSize(32);
        feature2.setPosition(200, 380);
        slide2.append(feature2);
        
        TextItem feature3 = new TextItem(1, "✦  Image Integration");
        feature3.setFontSize(32);
        feature3.setPosition(550, 320);
        slide2.append(feature3);
        
        TextItem feature4 = new TextItem(1, "✦  Shape Elements");
        feature4.setFontSize(32);
        feature4.setPosition(550, 380);
        slide2.append(feature4);
        
        // Add a hint with better visibility
        TextItem hint = new TextItem(2, "Press → or Page Down for next slide");
        hint.setFontSize(20);
        hint.setPosition(150, 520);
        slide2.append(hint);
        
        presentation.append(slide2);

        // Third slide - Getting Started
        Slide slide3 = new Slide();
        slide3.setTitle("Getting Started");
        
        // Add decorative background shape
        ShapeItem bgShape3 = new ShapeItem(0, "rectangle", new Color(65, 105, 225, 15));
        bgShape3.setSize(900, 600);
        bgShape3.setPosition(50, 50);
        slide3.append(bgShape3);
        
        // Add title
        TextItem startTitle = new TextItem(0, "Create Your First Presentation");
        startTitle.setFontSize(56);
        startTitle.setPosition(150, 180);
        slide3.append(startTitle);
        
        // Add instructions
        TextItem instr1 = new TextItem(1, "1. Click File → New for a fresh start");
        instr1.setFontSize(28);
        instr1.setPosition(200, 280);
        slide3.append(instr1);
        
        TextItem instr2 = new TextItem(1, "2. Use Edit menu to add content");
        instr2.setFontSize(28);
        instr2.setPosition(200, 330);
        slide3.append(instr2);
        
        TextItem instr3 = new TextItem(1, "3. Save your work with File → Save");
        instr3.setFontSize(28);
        instr3.setPosition(200, 380);
        slide3.append(instr3);
        
        // Add logo
        BitmapItem logo = new BitmapItem(1, "JabberPoint.jpg");
        logo.setPosition(550, 280);
        slide3.append(logo);
        
        presentation.append(slide3);

        return presentation;
    }
} 