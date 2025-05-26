package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.service.DialogService;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class AddSlideCommand implements Command {
    private final Presentation presentation;
    private final DialogService dialogService;

    public AddSlideCommand(Presentation presentation, DialogService dialogService) {
        this.presentation = presentation;
        this.dialogService = dialogService;
    }

    @Override
    public void execute() {
        Slide newSlide = new Slide();
        presentation.append(newSlide);
        presentation.goToSlide(presentation.getTotalSlides() - 1);
        
        dialogService.showMessageDialog("New blank slide added!\nUse the Edit menu to add content.");
    }
} 