package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExitCommand implements Command {
    private final JFrame frame;
    private final Presentation presentation;

    public ExitCommand(JFrame frame, Presentation presentation) {
        this.frame = frame;
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        if (presentation.hasUnsavedChanges()) {
            int response = JOptionPane.showConfirmDialog(frame,
                "Do you want to save the presentation before exiting?",
                "Save before exit?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
                
            if (response == JOptionPane.CANCEL_OPTION) {
                return;
            }
            
            if (response == JOptionPane.YES_OPTION) {
                new SaveCommand(presentation, frame).execute();
            }
        }
        
        frame.dispose();
        System.exit(0);
    }
} 