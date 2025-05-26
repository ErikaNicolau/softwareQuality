package com.jabberpoint.command;

import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Constants;
import com.jabberpoint.util.Position;
import java.awt.Color;

public class AddShapeCommand implements Command {
    private final Receiver receiver;
    private final DialogService dialogService;
    private final String shapeType;
    private final Color color;
    private final Position position;

    public AddShapeCommand(Receiver receiver, DialogService dialogService, String shapeType, Color color, Position position) {
        this.receiver = receiver;
        this.dialogService = dialogService;
        this.shapeType = shapeType;
        this.color = color;
        this.position = position;
    }

    @Override
    public void execute() {
        if (receiver.getCurrentSlide() == null) {
            dialogService.showMessageDialog("Please create a slide first using the Slide menu!");
            return;
        }
        try {
            ShapeItem shapeItem = new ShapeItem(Constants.DEFAULT_LEVEL, shapeType, color);
            shapeItem.setPosition(position.getX(), position.getY());
            shapeItem.setSize(position.getWidth(), position.getHeight());
            receiver.getCurrentSlide().append(shapeItem);
            dialogService.showMessageDialog("Shape added successfully!");
        } catch (IllegalArgumentException e) {
            dialogService.showMessageDialog("Error adding shape: " + e.getMessage());
        }
    }
} 