package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.util.XMLAccessor;
import com.jabberpoint.service.FileService;
import com.jabberpoint.service.DialogService;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;

public class SaveCommand implements Command {
    private final Presentation presentation;
    private final FileService fileService;
    private final DialogService dialogService;

    public SaveCommand(Presentation presentation, FileService fileService, DialogService dialogService) {
        this.presentation = presentation;
        this.fileService = fileService;
        this.dialogService = dialogService;
    }

    @Override
    public void execute() {
        String filename = presentation.getCurrentFileName();
        
        if (filename == null) {
            filename = fileService.getFilePathToSave();
            if (filename == null) {
                return;
            }
            if (!filename.endsWith(".xml")) {
                filename += ".xml";
            }
        }
        
        try {
            XMLAccessor xmlAccessor = new XMLAccessor();
            xmlAccessor.saveFile(presentation, filename);
            presentation.markAsSaved(filename);
            dialogService.showMessageDialog("Presentation saved successfully!");
        } catch (IOException e) {
            dialogService.showMessageDialog("Error saving presentation: " + e.getMessage());
        }
    }
} 