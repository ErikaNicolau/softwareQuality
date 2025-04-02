package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.items.ShapeItem;
import javax.swing.*;
import java.awt.*;

public class AddShapeCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public AddShapeCommand(Presentation presentation, JFrame frame) {
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

        // Create shape selection panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Shape type selection
        JLabel shapeLabel = new JLabel("Select shape:");
        String[] shapes = {"Rectangle", "Oval", "Line"};
        JComboBox<String> shapeCombo = new JComboBox<>(shapes);
        
        // Color selection
        JLabel colorLabel = new JLabel("Select color:");
        String[] colors = {"Black", "Red", "Blue", "Green"};
        JComboBox<String> colorCombo = new JComboBox<>(colors);

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
        JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 500, 10));
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 500, 10));
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sizePanel.add(new JLabel("Width:"));
        sizePanel.add(widthSpinner);
        sizePanel.add(new JLabel("Height:"));
        sizePanel.add(heightSpinner);
        
        // Add all components
        panel.add(shapeLabel);
        panel.add(shapeCombo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(colorLabel);
        panel.add(colorCombo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(posLabel);
        panel.add(posPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sizeLabel);
        panel.add(sizePanel);
        
        int result = JOptionPane.showConfirmDialog(frame, panel, 
            "Add Shape", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            String selectedShape = (String) shapeCombo.getSelectedItem();
            String selectedColor = (String) colorCombo.getSelectedItem();
            
            Color color = switch (selectedColor) {
                case "Red" -> Color.RED;
                case "Blue" -> Color.BLUE;
                case "Green" -> Color.GREEN;
                default -> Color.BLACK;
            };
            
            ShapeItem shapeItem = new ShapeItem(1, selectedShape, color);
            shapeItem.setPosition((Integer)xSpinner.getValue(), (Integer)ySpinner.getValue());
            shapeItem.setSize((Integer)widthSpinner.getValue(), (Integer)heightSpinner.getValue());
            presentation.getCurrentSlide().append(shapeItem);
            JOptionPane.showMessageDialog(frame, 
                "Shape added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
} 