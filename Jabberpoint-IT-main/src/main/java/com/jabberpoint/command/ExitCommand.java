package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExitCommand implements Command {
    private final JFrame frame;
    private final Presentation presentation;

    // Flag to indicate if System.exit(0) should be skipped (for testing)
    private static boolean skipSystemExit = false;

    // Method for tests to set the skip flag
    public static void setSkipSystemExit(boolean skip) {
        skipSystemExit = skip;
    }

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
        if (!skipSystemExit) {
            System.exit(0);
        }
    }
} 