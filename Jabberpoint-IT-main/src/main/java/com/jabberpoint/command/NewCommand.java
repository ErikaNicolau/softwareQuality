package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Constants;
import java.awt.Color;

public class NewCommand implements Command {
    private final Presentation presentation;
    private final DialogService dialogService;

    public NewCommand(Presentation presentation, DialogService dialogService) {
        this.presentation = presentation;
        this.dialogService = dialogService;
    }

    @Override
    public void execute() {
        presentation.clear();
        Slide newSlide = new Slide();
        newSlide.setTitle("Welcome to JabberPoint");
        ShapeItem decorativeShape = new ShapeItem(0, "rectangle", new Color(70, 130, 180, 30));
        decorativeShape.setSize(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        decorativeShape.setPosition(100, 100);
        newSlide.append(decorativeShape);
        TextItem titleItem = new TextItem(0, "Welcome to JabberPoint");
        titleItem.setFontSize(Constants.TITLE_FONT_SIZE);
        titleItem.setPosition(150, 180);
        newSlide.append(titleItem);
        TextItem subtitleItem = new TextItem(1, "Create Professional Presentations");
        subtitleItem.setFontSize(Constants.DEFAULT_FONT_SIZE);
        subtitleItem.setPosition(150, 260);
        newSlide.append(subtitleItem);
        TextItem feature1 = new TextItem(2, "✦  Rich Text Support");
        feature1.setFontSize(Constants.DEFAULT_FONT_SIZE);
        feature1.setPosition(200, 350);
        newSlide.append(feature1);
        TextItem feature2 = new TextItem(2, "✦  Image Integration");
        feature2.setFontSize(Constants.DEFAULT_FONT_SIZE);
        feature2.setPosition(200, 400);
        newSlide.append(feature2);
        TextItem feature3 = new TextItem(2, "✦  Shape Elements");
        feature3.setFontSize(Constants.DEFAULT_FONT_SIZE);
        feature3.setPosition(500, 350);
        newSlide.append(feature3);
        TextItem feature4 = new TextItem(2, "✦  Smooth Navigation");
        feature4.setFontSize(Constants.DEFAULT_FONT_SIZE);
        feature4.setPosition(500, 400);
        newSlide.append(feature4);
        TextItem hint = new TextItem(1, "Press 'Edit' menu to start creating your presentation");
        hint.setFontSize(Constants.DEFAULT_FONT_SIZE);
        hint.setPosition(150, 500);
        newSlide.append(hint);
        presentation.append(newSlide);
        dialogService.showMessageDialog("Welcome to JabberPoint!\nUse the Edit menu to add content to your presentation.");
    }
} 