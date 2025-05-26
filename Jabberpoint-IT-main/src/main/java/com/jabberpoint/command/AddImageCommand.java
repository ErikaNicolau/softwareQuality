package com.jabberpoint.command;

import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Constants;
import com.jabberpoint.util.Position;

public class AddImageCommand implements Command {
    private final Receiver receiver;
    private final DialogService dialogService;
    private final String imagePath;
    private final Position position;

    public AddImageCommand(Receiver receiver, DialogService dialogService, String imagePath, Position position) {
        this.receiver = receiver;
        this.dialogService = dialogService;
        this.imagePath = imagePath;
        this.position = position;
    }

    @Override
    public void execute() {
        if (receiver.getCurrentSlide() == null) {
            dialogService.showMessageDialog("Please create a slide first using the Slide menu!");
            return;
        }
        try {
            BitmapItem bitmapItem = new BitmapItem(Constants.DEFAULT_LEVEL, imagePath);
            bitmapItem.setPosition(position.getX(), position.getY());
            bitmapItem.setSize(position.getWidth(), position.getHeight());
            receiver.getCurrentSlide().append(bitmapItem);
        } catch (Exception e) {
            dialogService.showMessageDialog("Error loading image: " + e.getMessage());
        }
    }
} 