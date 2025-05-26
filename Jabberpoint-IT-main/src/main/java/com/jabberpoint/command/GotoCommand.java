package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;

public class GotoCommand implements Command {
    private final Presentation presentation;
    private final int slideNumber;

    public GotoCommand(Presentation presentation, int slideNumber) {
        this.presentation = presentation;
        this.slideNumber = slideNumber;
    }

    @Override
    public void execute() {
        presentation.goToSlide(slideNumber);
    }
} 