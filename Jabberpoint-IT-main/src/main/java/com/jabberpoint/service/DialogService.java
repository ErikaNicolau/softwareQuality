package com.jabberpoint.service;

public interface DialogService {
    String showInputDialog(String message);
    void showMessageDialog(String message);
    boolean showConfirmDialog(String message);
} 