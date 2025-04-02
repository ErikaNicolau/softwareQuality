package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class AddSlideCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public AddSlideCommand(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        Slide newSlide = new Slide();
        presentation.append(newSlide);
        presentation.goToSlide(presentation.getTotalSlides() - 1); // Go to the new slide
        
        JOptionPane.showMessageDialog(frame, 
            "New blank slide added!\nUse the Edit menu to add content.", 
            "Slide Added", 
            JOptionPane.INFORMATION_MESSAGE);
    }
} 