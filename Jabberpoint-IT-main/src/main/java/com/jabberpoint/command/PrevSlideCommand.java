package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;

public class PrevSlideCommand implements Command {
    private final Presentation presentation;

    public PrevSlideCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.previousSlide();
    }
} 