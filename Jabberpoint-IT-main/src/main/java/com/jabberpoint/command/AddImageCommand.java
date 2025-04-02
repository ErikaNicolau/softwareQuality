package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.items.BitmapItem;
import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class AddImageCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public AddImageCommand(Presentation presentation, JFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        if (presentation.getCurrentSlide() == null) {
            JOptionPane.showMessageDialog(frame, 
                "Please create a slide first using the Slide menu!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif", "bmp"));
        
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Create settings panel
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            // Position controls
            JLabel posLabel = new JLabel("Position (x, y):");
            JSpinner xSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000, 10));
            JSpinner ySpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000, 10));
            JPanel posPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            posPanel.add(new JLabel("X:"));
            posPanel.add(xSpinner);
            posPanel.add(new JLabel("Y:"));
            posPanel.add(ySpinner);

            // Size controls
            JLabel sizeLabel = new JLabel("Size (width, height):");
            JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(400, 50, 1000, 50));
            JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(300, 50, 1000, 50));
            JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            sizePanel.add(new JLabel("Width:"));
            sizePanel.add(widthSpinner);
            sizePanel.add(new JLabel("Height:"));
            sizePanel.add(heightSpinner);
            
            // Add components
            panel.add(posLabel);
            panel.add(posPanel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(sizeLabel);
            panel.add(sizePanel);
            
            int settingsResult = JOptionPane.showConfirmDialog(frame, panel,
                "Image Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
            if (settingsResult == JOptionPane.OK_OPTION) {
                try {
                    BitmapItem bitmapItem = new BitmapItem(1, selectedFile.getAbsolutePath());
                    bitmapItem.setPosition((Integer)xSpinner.getValue(), (Integer)ySpinner.getValue());
                    bitmapItem.setSize((Integer)widthSpinner.getValue(), (Integer)heightSpinner.getValue());
                    presentation.getCurrentSlide().append(bitmapItem);
                    JOptionPane.showMessageDialog(frame, 
                        "Image added successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame,
                        "Error loading image: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
} 