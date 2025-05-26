package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.service.DialogService;
import javax.swing.*;
import java.awt.*;

public class AddTextCommand implements Command {
    private final Presentation presentation;
    private final DialogService dialogService;
    private final String text;
    private final int level;
    private final int x;
    private final int y;
    private final int fontSize;

    public AddTextCommand(Presentation presentation, DialogService dialogService, String text, int level, int x, int y, int fontSize) {
        this.presentation = presentation;
        this.dialogService = dialogService;
        this.text = text;
        this.level = level;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
    }

    @Override
    public void execute() {
        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide == null) {
            dialogService.showMessageDialog("Please create a slide first using the Slide menu!");
            return;
        }

        TextItem textItem = new TextItem(level, text);
        textItem.setPosition(x, y);
        textItem.setFontSize(fontSize);
        currentSlide.append(textItem);
    }
} 