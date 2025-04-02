package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.util.XMLAccessor;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;

public class OpenCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public OpenCommand(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Presentation");
        
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                XMLAccessor xmlAccessor = new XMLAccessor();
                xmlAccessor.loadFile(presentation, fileChooser.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frame, "Presentation loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error loading presentation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 