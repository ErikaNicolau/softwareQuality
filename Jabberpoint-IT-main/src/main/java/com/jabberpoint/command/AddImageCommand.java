package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.service.FileService;
import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class AddImageCommand implements Command {
    private final Presentation presentation;
    private final DialogService dialogService;
    private final String imagePath;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    // Constructor with parameters for image details
    public AddImageCommand(Presentation presentation, DialogService dialogService, String imagePath, int x, int y, int width, int height) {
        this.presentation = presentation;
        this.dialogService = dialogService;
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void execute() {
        if (presentation.getCurrentSlide() == null) {
            dialogService.showMessageDialog("Please create a slide first using the Slide menu!");
            return;
        }

        try {
            BitmapItem bitmapItem = new BitmapItem(1, imagePath);
            bitmapItem.setPosition(x, y);
            bitmapItem.setSize(width, height);
            presentation.getCurrentSlide().append(bitmapItem);
        } catch (Exception e) {
            dialogService.showMessageDialog("Error loading image: " + e.getMessage());
        }
    }
} 