package com.jabberpoint.service.swing;

import com.jabberpoint.service.DialogService;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SwingDialogService implements DialogService {
    private final JFrame parentFrame;

    public SwingDialogService(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public String showInputDialog(String message) {
        return JOptionPane.showInputDialog(parentFrame, message);
    }

    @Override
    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(parentFrame, message);
    }

    @Override
    public boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(parentFrame, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
} 