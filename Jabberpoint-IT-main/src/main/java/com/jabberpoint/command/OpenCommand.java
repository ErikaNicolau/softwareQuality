package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.util.XMLAccessor;
import com.jabberpoint.service.FileService;
import com.jabberpoint.service.DialogService;
import java.io.IOException;

public class OpenCommand implements Command {
    private final Presentation presentation;
    private final FileService fileService;
    private final DialogService dialogService;

    public OpenCommand(Presentation presentation, FileService fileService, DialogService dialogService) {
        this.presentation = presentation;
        this.fileService = fileService;
        this.dialogService = dialogService;
    }

    @Override
    public void execute() {
        String filePath = fileService.getFilePathToOpen();
        if (filePath != null) {
            try {
                XMLAccessor xmlAccessor = new XMLAccessor();
                xmlAccessor.loadFile(presentation, filePath);
                dialogService.showMessageDialog("Presentation loaded successfully!");
            } catch (IOException e) {
                dialogService.showMessageDialog("Error loading presentation: " + e.getMessage());
            }
        }
    }
} 