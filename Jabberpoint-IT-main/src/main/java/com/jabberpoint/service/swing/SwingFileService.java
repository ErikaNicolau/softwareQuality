package com.jabberpoint.service.swing;

import com.jabberpoint.service.FileService;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SwingFileService implements FileService {
    private final JFrame parentFrame;

    public SwingFileService(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public String getFilePathToOpen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Presentation");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    @Override
    public String getFilePathToSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Presentation");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        
        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
} 