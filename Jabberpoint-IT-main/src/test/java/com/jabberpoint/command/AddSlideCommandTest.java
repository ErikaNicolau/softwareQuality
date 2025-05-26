package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import org.junit.jupiter.api.Test;
import javax.swing.JFrame;

import static org.mockito.Mockito.*;

class AddSlideCommandTest {

    @Test
    void execute_shouldAddNewSlideAndNavigate() {
        Presentation mockPresentation = mock(Presentation.class);
        JFrame mockFrame = mock(JFrame.class);
        AddSlideCommand command = new AddSlideCommand(mockPresentation, mockFrame);

        when(mockPresentation.getTotalSlides()).thenReturn(1);

        command.execute();

        verify(mockPresentation, times(1)).append(any(Slide.class));
        verify(mockPresentation, times(1)).goToSlide(1);
    }
} 