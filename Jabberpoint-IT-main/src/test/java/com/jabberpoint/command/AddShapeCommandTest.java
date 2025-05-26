package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

public class AddShapeCommandTest {

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
    void execute_addsShapeItemToCurrentSlide() {
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide).thenReturn(mockSlide);
        String shapeType = "rectangle";
        Color color = Color.RED;
        int x = 10;
        int y = 20;

        AddShapeCommand command = new AddShapeCommand(mockPresentation, mockDialogService, shapeType, color, new Position(x, y));
        command.execute();

        verify(mockPresentation, times(2)).getCurrentSlide();
        verify(mockSlide).append(any());
        verify(mockDialogService).showMessageDialog("Shape added successfully!");
        verifyNoMoreInteractions(mockPresentation, mockSlide, mockDialogService);
    }

    @Test
    void execute_showsMessageWhenNoSlideExists() {
        when(mockPresentation.getCurrentSlide()).thenReturn(null);
        String shapeType = "rectangle";
        Color color = Color.RED;
        int x = 10;
        int y = 20;

        AddShapeCommand command = new AddShapeCommand(mockPresentation, mockDialogService, shapeType, color, new Position(x, y));
        command.execute();

        verify(mockPresentation).getCurrentSlide();
        verify(mockDialogService).showMessageDialog("Please create a slide first using the Slide menu!");
        verifyNoMoreInteractions(mockPresentation, mockSlide, mockDialogService);
    }
} 