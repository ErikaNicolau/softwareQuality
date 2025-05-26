package com.jabberpoint.test.mock;

import com.jabberpoint.service.FileService;

import java.io.File;

public class MockFileService implements FileService {

    private File selectedFile = null;
    private String filePathToOpen;
    private String filePathToSave;

    public void setSelectedFile(File file) {
        this.selectedFile = file;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setFilePathToOpen(String path) {
        this.filePathToOpen = path;
    }

    public void setFilePathToSave(String path) {
        this.filePathToSave = path;
    }

    public String getFilePathToOpen() {
        return filePathToOpen;
    }

    public String getFilePathToSave() {
        return filePathToSave;
    }
} 