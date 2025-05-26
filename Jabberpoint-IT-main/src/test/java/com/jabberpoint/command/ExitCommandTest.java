package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.JFrame;

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
        // Prevent System.exit(0) during tests
        ExitCommand.setSkipSystemExit(true);
    }

    @Test
    void execute_shouldDisposeFrameWhenNoUnsavedChanges() {
        when(presentation.hasUnsavedChanges()).thenReturn(false);

        exitCommand.execute();

        verify(frame).dispose();
        verifyNoInteractions(dialogService);
        verifyNoInteractions(fileService);
    }
} 