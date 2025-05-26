package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.JFrame;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExitCommandTest {

    @Mock
    private JFrame frame;
    @Mock
    private Presentation presentation;
    @Mock
    private FileService fileService;
    @Mock
    private DialogService dialogService;

    private ExitCommand exitCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exitCommand = new ExitCommand(frame, presentation, fileService, dialogService);
        ExitCommand.setSkipSystemExit(true); // Skip System.exit(0) for testing
    }

    @Test
    void execute_shouldExitWithoutPromptWhenNoUnsavedChanges() {
        when(presentation.hasUnsavedChanges()).thenReturn(false);

        exitCommand.execute();

        verify(frame).dispose();
        verifyNoInteractions(dialogService);
        verifyNoInteractions(fileService);
    }

    @Test
    void execute_shouldPromptAndSaveWhenUserConfirms() {
        when(presentation.hasUnsavedChanges()).thenReturn(true);
        when(dialogService.showConfirmDialog("Do you want to save the presentation before exiting?"))
            .thenReturn(true);

        exitCommand.execute();

        verify(dialogService).showConfirmDialog("Do you want to save the presentation before exiting?");
        verify(frame).dispose();
    }

    @Test
    void execute_shouldExitWithoutSavingWhenUserDeclines() {
        when(presentation.hasUnsavedChanges()).thenReturn(true);
        when(dialogService.showConfirmDialog("Do you want to save the presentation before exiting?"))
            .thenReturn(false);

        exitCommand.execute();

        verify(dialogService).showConfirmDialog("Do you want to save the presentation before exiting?");
        verify(frame, never()).dispose();
        verifyNoInteractions(fileService);
    }
} 