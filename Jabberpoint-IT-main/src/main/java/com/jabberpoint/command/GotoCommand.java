package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GotoCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public GotoCommand(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        String input = JOptionPane.showInputDialog(frame, "Enter slide number:", "Go to Slide", JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                int slideNumber = Integer.parseInt(input.trim());
                presentation.goToSlide(slideNumber - 1);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 