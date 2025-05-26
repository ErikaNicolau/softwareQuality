package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class AddTextCommandTest {
    private Presentation mockPresentation;
    private DialogService mockDialogService;
    private Slide mockSlide;

    @BeforeEach
    void setUp() {
        mockPresentation = mock(Presentation.class);
        mockDialogService = mock(DialogService.class);
        mockSlide = mock(Slide.class);
        
    }

    @Test
    void execute_addsTextItemToCurrentSlide() {
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
        String text = "Test Text";
        int x = 10;
        int y = 20;
        
        AddTextCommand command = new AddTextCommand(mockPresentation, mockDialogService, text, new Position(x, y));
        command.execute();

        InOrder inOrder = inOrder(mockPresentation, mockSlide);
        inOrder.verify(mockPresentation).getCurrentSlide();
        inOrder.verify(mockSlide).append(any()); // Verify append was called with some TextItem
        verifyNoMoreInteractions(mockPresentation, mockSlide, mockDialogService);
    }

    @Test
    void execute_showsMessageWhenNoSlideExists() {
        when(mockPresentation.getCurrentSlide()).thenReturn(null);
        String text = "Test Text";
        int x = 10;
        int y = 20;

        AddTextCommand command = new AddTextCommand(mockPresentation, mockDialogService, text, new Position(x, y));
        command.execute();

        verify(mockPresentation).getCurrentSlide();
        verify(mockDialogService).showMessageDialog("Please create a slide first using the Slide menu!");
        verifyNoMoreInteractions(mockPresentation, mockSlide, mockDialogService);
    }
} 