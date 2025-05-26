package com.jabberpoint.command;

import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Constants;
import com.jabberpoint.util.Position;

public class AddTextCommand implements Command {
    private final Receiver receiver;
    private final DialogService dialogService;
    private final String text;
    private final Position position;

    public AddTextCommand(Receiver receiver, DialogService dialogService, String text, Position position) {
        this.receiver = receiver;
        this.dialogService = dialogService;
        this.text = text;
        this.position = position;
    }

    @Override
    public void execute() {
        Slide currentSlide = receiver.getCurrentSlide();
        if (currentSlide == null) {
            dialogService.showMessageDialog("Please create a slide first using the Slide menu!");
            return;
        }
        TextItem textItem = new TextItem(Constants.DEFAULT_LEVEL, text);
        textItem.setPosition(position.getX(), position.getY());
        textItem.setSize(position.getWidth(), position.getHeight());
        textItem.setFontSize(Constants.DEFAULT_FONT_SIZE);
        currentSlide.append(textItem);
    }
} 