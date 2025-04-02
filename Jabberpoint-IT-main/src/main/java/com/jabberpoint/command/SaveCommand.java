package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.util.XMLAccessor;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;

public class SaveCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public SaveCommand(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        String filename = presentation.getCurrentFileName();
        
        if (filename == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Presentation");
            
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                filename = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filename.endsWith(".xml")) {
                    filename += ".xml";
                }
            } else {
                return;
            }
        }
        
        try {
            XMLAccessor xmlAccessor = new XMLAccessor();
            xmlAccessor.saveFile(presentation, filename);
            presentation.markAsSaved(filename);
            JOptionPane.showMessageDialog(frame, "Presentation saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving presentation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 