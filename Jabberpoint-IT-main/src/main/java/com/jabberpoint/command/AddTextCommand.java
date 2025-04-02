package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.items.TextItem;
import javax.swing.*;
import java.awt.*;

public class AddTextCommand implements Command {
    private final Presentation presentation;
    private final JFrame frame;

    public AddTextCommand(Presentation presentation, JFrame frame) {
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

        // Create input panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Text input
        JLabel textLabel = new JLabel("Enter text:");
        JTextField textField = new JTextField(30);

        // Position controls
        JLabel posLabel = new JLabel("Position (x, y):");
        JSpinner xSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000, 10));
        JSpinner ySpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000, 10));
        JPanel posPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        posPanel.add(new JLabel("X:"));
        posPanel.add(xSpinner);
        posPanel.add(new JLabel("Y:"));
        posPanel.add(ySpinner);

        // Font size control
        JLabel sizeLabel = new JLabel("Font size:");
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(24, 8, 72, 2));

        // Add components
        panel.add(textLabel);
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(posLabel);
        panel.add(posPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sizeLabel);
        panel.add(sizeSpinner);

        int result = JOptionPane.showConfirmDialog(frame, panel,
            "Add Text", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && !textField.getText().trim().isEmpty()) {
            TextItem textItem = new TextItem(1, textField.getText().trim());
            textItem.setPosition((Integer)xSpinner.getValue(), (Integer)ySpinner.getValue());
            textItem.setFontSize((Integer)sizeSpinner.getValue());
            presentation.getCurrentSlide().append(textItem);
        }
    }
} 