package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.service.FileService;
import javax.swing.JFrame;

public class ExitCommand implements Command {
    private final JFrame frame;
    private final Presentation presentation;
    private final FileService fileService;
    private final DialogService dialogService;
    private static boolean skipSystemExit = false;

    public static void setSkipSystemExit(boolean skip) {
        skipSystemExit = skip;
    }

    public ExitCommand(JFrame frame, Presentation presentation, FileService fileService, DialogService dialogService) {
        this.frame = frame;
        this.presentation = presentation;
        this.fileService = fileService;
        this.dialogService = dialogService;
    }

    @Override
    public void execute() {
        if (presentation.hasUnsavedChanges()) {
            boolean shouldSave = dialogService.showConfirmDialog("Do you want to save the presentation before exiting?");
            if (!shouldSave) {
                return;
            }
            new SaveCommand(presentation, fileService, dialogService).execute();
        }
        frame.dispose();
        if (!skipSystemExit) {
            System.exit(0);
        }
    }
} 