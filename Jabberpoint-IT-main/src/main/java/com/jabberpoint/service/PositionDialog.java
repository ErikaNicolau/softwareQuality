package com.jabberpoint.service;

import javax.swing.*;
import java.awt.*;
import com.jabberpoint.util.Position;

public class PositionDialog {
    private final JTextField xField;
    private final JTextField yField;
    private final JTextField widthField;
    private final JTextField heightField;
    private final JDialog dialog;

    public PositionDialog(Frame parent) {
        dialog = new JDialog(parent, "Set Position", true);
        dialog.setLayout(new GridLayout(5, 2, 5, 5));

        xField = new JTextField("50");
        yField = new JTextField("50");
        widthField = new JTextField("400");
        heightField = new JTextField("300");

        dialog.add(new JLabel("X Position:"));
        dialog.add(xField);
        dialog.add(new JLabel("Y Position:"));
        dialog.add(yField);
        dialog.add(new JLabel("Width:"));
        dialog.add(widthField);
        dialog.add(new JLabel("Height:"));
        dialog.add(heightField);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> dialog.dispose());
        cancelButton.addActionListener(e -> {
            xField.setText("");
            yField.setText("");
            widthField.setText("");
            heightField.setText("");
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
    }

    public Position showDialog() {
        dialog.setVisible(true);
        
        try {
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());
            int width = Integer.parseInt(widthField.getText().trim());
            int height = Integer.parseInt(heightField.getText().trim());
            
            if (x >= 0 && y >= 0 && width > 0 && height > 0) {
                return new Position(x, y, width, height);
            }
        } catch (NumberFormatException ignored) {
            // Invalid input, return null
        }
        return null;
    }
} 