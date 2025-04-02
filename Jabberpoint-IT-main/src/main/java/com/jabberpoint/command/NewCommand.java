package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.ShapeItem;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class NewCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public NewCommand(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        presentation.clear();
        
        // Create a new slide
        Slide newSlide = new Slide();
        newSlide.setTitle("Welcome to JabberPoint");
        
        // Add a decorative shape as background
        ShapeItem decorativeShape = new ShapeItem(0, "rectangle", new Color(70, 130, 180, 30));
        decorativeShape.setSize(800, 500);
        decorativeShape.setPosition(100, 100);
        newSlide.append(decorativeShape);
        
        // Add main title with large font
        TextItem titleItem = new TextItem(0, "Welcome to JabberPoint");
        titleItem.setFontSize(64);
        titleItem.setPosition(150, 180);
        newSlide.append(titleItem);
        
        // Add subtitle with medium font
        TextItem subtitleItem = new TextItem(1, "Create Professional Presentations");
        subtitleItem.setFontSize(36);
        subtitleItem.setPosition(150, 260);
        newSlide.append(subtitleItem);
        
        // Add feature list with organized layout and larger font
        TextItem feature1 = new TextItem(2, "✦  Rich Text Support");
        feature1.setFontSize(28);
        feature1.setPosition(200, 350);
        newSlide.append(feature1);
        
        TextItem feature2 = new TextItem(2, "✦  Image Integration");
        feature2.setFontSize(28);
        feature2.setPosition(200, 400);
        newSlide.append(feature2);
        
        TextItem feature3 = new TextItem(2, "✦  Shape Elements");
        feature3.setFontSize(28);
        feature3.setPosition(500, 350);
        newSlide.append(feature3);
        
        TextItem feature4 = new TextItem(2, "✦  Smooth Navigation");
        feature4.setFontSize(28);
        feature4.setPosition(500, 400);
        newSlide.append(feature4);
        
        // Add a hint at the bottom with better visibility
        TextItem hint = new TextItem(1, "Press 'Edit' menu to start creating your presentation");
        hint.setFontSize(24);
        hint.setPosition(150, 500);
        newSlide.append(hint);
        
        // Add the slide to the presentation
        presentation.append(newSlide);
        
        JOptionPane.showMessageDialog(frame, 
            "Welcome to JabberPoint!\nUse the Edit menu to add content to your presentation.", 
            "New Presentation", 
            JOptionPane.INFORMATION_MESSAGE);
    }
} 