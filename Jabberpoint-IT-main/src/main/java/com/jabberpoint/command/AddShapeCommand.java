package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.service.DialogService;
import javax.swing.*;
import java.awt.*;

public class AddShapeCommand implements Command {
    private final Presentation presentation;
    private final DialogService dialogService;
    private final String shapeType;
    private final Color color;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public AddShapeCommand(Presentation presentation, DialogService dialogService, String shapeType, Color color, int x, int y, int width, int height) {
        this.presentation = presentation;
        this.dialogService = dialogService;
        this.shapeType = shapeType;
        this.color = color;
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
            ShapeItem shapeItem = new ShapeItem(1, shapeType, color);
            shapeItem.setPosition(x, y);
            shapeItem.setSize(width, height);
            presentation.getCurrentSlide().append(shapeItem);
            dialogService.showMessageDialog("Shape added successfully!");
        } catch (IllegalArgumentException e) {
            dialogService.showMessageDialog("Error adding shape: " + e.getMessage());
        }
    }
} 