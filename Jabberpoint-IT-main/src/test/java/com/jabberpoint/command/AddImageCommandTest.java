package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AddImageCommandTest {

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
    void execute_addsBitmapItemToCurrentSlide() {
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide).thenReturn(mockSlide); // Expect two calls
        String imagePath = "test.jpg";
        int x = 10;
        int y = 20;

        AddImageCommand command = new AddImageCommand(mockPresentation, mockDialogService, imagePath, new Position(x, y));
        command.execute();

        // Verify interactions without strict in-order checking for getCurrentSlide
        verify(mockPresentation, times(2)).getCurrentSlide(); // Expecting two calls
        verify(mockSlide).append(any()); // Verify append was called with some BitmapItem
        verifyNoMoreInteractions(mockPresentation, mockSlide, mockDialogService);
    }

    @Test
    void execute_showsMessageWhenNoSlideExists() {
        when(mockPresentation.getCurrentSlide()).thenReturn(null);
        String imagePath = "test.jpg";
        int x = 10;
        int y = 20;

        AddImageCommand command = new AddImageCommand(mockPresentation, mockDialogService, imagePath, new Position(x, y));
        command.execute();

        verify(mockPresentation).getCurrentSlide();
        verify(mockDialogService).showMessageDialog("Please create a slide first using the Slide menu!");
        verifyNoMoreInteractions(mockPresentation, mockSlide, mockDialogService);
    }
} 